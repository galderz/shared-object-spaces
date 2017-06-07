package sos;

import java.util.HashMap;
import java.util.Map;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.factories.GlobalComponentRegistry;
import org.infinispan.lifecycle.AbstractModuleLifecycle;

import sos.pojos.Country;
import sos.pojos.Person;

public class LifecycleCallbacks extends AbstractModuleLifecycle {

   @Override
   public void cacheManagerStarting(GlobalComponentRegistry gcr, GlobalConfiguration global) {
      Map<Integer, AdvancedExternalizer<?>> map = new HashMap<>();
      map.put(1001, new Person.Externalizer());
      map.put(1002, new Country.Externalizer());

      global.serialization().advancedExternalizers().putAll(map);
   }

}
