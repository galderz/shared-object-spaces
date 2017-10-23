package prototype.example.pojos;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.factories.GlobalComponentRegistry;
import prototype.infinispan.Space;

import java.util.HashMap;
import java.util.Map;

public class CountrySpace implements Space {

   private AdvancedExternalizer<Object> externalizer =
      new Country.Externalizer();

   // TODO: Make it pluggable - DataContainer impl would be fine...
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
   public void init(GlobalComponentRegistry cr) {
      countries.put("Spain", new Country("Spain", "EUR"));
   }

}
