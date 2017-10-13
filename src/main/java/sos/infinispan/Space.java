package sos.infinispan;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.factories.ComponentRegistry;
import org.infinispan.factories.GlobalComponentRegistry;
import org.infinispan.manager.EmbeddedCacheManager;

public interface Space {

   // Used to get a reference to OGM at initialization
   // GlobalComponentRegistry can be retrieved from ComponentRegistry
   void init(ComponentRegistry cr);

   // True/false
   boolean initializeFor(ComponentRegistry cr);

   // Cache name for which this space is registered
   //
   // Note:
   // Added so that space can use cache name to register it in the global component registry
   // Spaces need to be looked up when cache manager is starting so that externalizer can be registered.
   String cacheName();

   AdvancedExternalizer externalizer();

   Object get(Object key);

}
