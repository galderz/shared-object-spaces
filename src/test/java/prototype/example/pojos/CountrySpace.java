package prototype.example.pojos;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.container.DataContainer;
import org.infinispan.factories.GlobalComponentRegistry;
import prototype.infinispan.Space;

public class CountrySpace implements Space<String, Country> {

   public static final String NAME = "CountrySpace";

   private AdvancedExternalizer<Object> externalizer =
      new Country.Externalizer();

   private DataContainer<String, Country> countries;

   public CountrySpace() {
      System.out.printf("@%x%n", System.identityHashCode(this));
   }

   @Override
   public AdvancedExternalizer<Object> externalizer() {
      return externalizer;
   }

   @Override
   public Country get(String key) {
      return countries.get(key).getValue();
   }

   @Override
   public String name() {
      return NAME;
   }

   @Override
   public long size() {
      return -1; // unbounded
   }

   @Override
   public void init(GlobalComponentRegistry cr, DataContainer<String, Country> container) {
      countries = container;
      countries.put("Spain", new Country("Spain", "EUR"), null);
   }

}
