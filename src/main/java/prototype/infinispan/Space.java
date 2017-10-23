package prototype.infinispan;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.factories.GlobalComponentRegistry;

/**
 * Since externalizers are global, it makes sense to make Space instances
 * global too, otherwise you could get a false sense that externalizers
 * are per cache, which are not.
 *
 * An advantage of having global Space instances that you could potentially
 * share them between data in multiple caches.
 */
public interface Space {

   // Used to get a reference to OGM at initialization
   void init(GlobalComponentRegistry cr);

   AdvancedExternalizer externalizer();

   Object get(Object key);

}
