package sos;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

class Cluster {

   static <K, V> void withCluster(BiConsumer<Cache<K, V>, Cache<K, V>> task) {
      CompletableFuture<Cache<K, V>> f1 = supplyAsync(() -> mkCache("sos.1"));
      CompletableFuture<Cache<K, V>> f2 = supplyAsync(() -> mkCache("sos.2"));
      try {
         CompletableFuture.allOf(f1, f2).thenAccept(x -> {
            Cache<K, V> c1 = f1.join();
            Cache<K, V> c2 = f2.join();
            try {
               System.out.println(c1.getCacheManager().getMembers());
               System.out.println(c2.getCacheManager().getMembers());
               task.accept(c1, c2);
            } finally {
               c1.getCacheManager().stop();
               c2.getCacheManager().stop();
            }
         }).get(5000, TimeUnit.SECONDS);
      } catch (Exception e) {
         throw new AssertionError(e);
      }
   }

   private static <K, V> Cache<K, V> mkCache(String domain) {
      GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
      global.globalJmxStatistics().jmxDomain(domain);
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.clustering().cacheMode(CacheMode.DIST_SYNC).hash().numOwners(2);
      EmbeddedCacheManager cm = new DefaultCacheManager(global.build(), builder.build());
      return cm.getCache();
   }

}
