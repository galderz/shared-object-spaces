package sos;

import java.util.HashMap;
import java.util.Map;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.factories.GlobalComponentRegistry;
import org.infinispan.lifecycle.AbstractModuleLifecycle;

import sos.pojos.Country;
import sos.pojos.CountryFactory;
import sos.pojos.Person;

public class LifecycleCallbacks extends AbstractModuleLifecycle {

   @Override
   public void cacheManagerStarting(GlobalComponentRegistry gcr, GlobalConfiguration global) {
      Person.Externalizer personExt = new Person.Externalizer();
      Country.Externalizer countryExt = new Country.Externalizer();

      CountryFactory countryFactory = new CountryFactory();
      gcr.registerComponent(countryFactory, CountryFactory.class);
      gcr.registerComponent(personExt, personExt.getClass().getName());
      gcr.registerComponent(countryExt, countryExt.getClass().getName());

      Map<Integer, AdvancedExternalizer<?>> map = new HashMap<>();
      map.put(1001, personExt);
      map.put(1002, countryExt);

      global.serialization().advancedExternalizers().putAll(map);
   }

}
