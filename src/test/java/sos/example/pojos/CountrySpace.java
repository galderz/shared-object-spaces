package sos.example.pojos;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.factories.ComponentRegistry;
import sos.infinispan.Space;

import java.util.HashMap;
import java.util.Map;

public class CountrySpace implements Space {

   private AdvancedExternalizer<Object> externalizer =
      new Country.Externalizer();

   // TODO: Make it pluggable
   private final Map<Object, Object> countries = new HashMap<>();

   public CountrySpace() {
      System.out.printf("@%x%n", System.identityHashCode(this));
   }

   @Override
   public AdvancedExternalizer<Object> externalizer() {
      return externalizer;
   }

   @Override
   public Object get(Object key) {
      return countries.get(key);
   }

   @Override
   public void init(ComponentRegistry cr) {
      countries.put("Spain", new Country("Spain", "EUR"));
   }

   @Override
   public String cacheName() {
      return "inhabitants";
   }

}
