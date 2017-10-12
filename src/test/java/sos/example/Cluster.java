package sos.example;

import java.util.function.BiConsumer;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

class Cluster {

   static <K, V> void withCluster(BiConsumer<Cache<K, V>, Cache<K, V>> task) {
      Cache<K, V> c0 = mkCache("sos.1");
      Cache<K, V> c1 = mkCache("sos.2");
      try {
         task.accept(c0, c1);
      } finally {
         c0.getCacheManager().stop();
         c1.getCacheManager().stop();
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
