package sos.pojos;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.infinispan.commons.marshall.AdvancedExternalizer;
import sos.infinispan.Space;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class CountrySpace implements Space {

   private AdvancedExternalizer<Object> externalizer =
      new Country.Externalizer();

   // TODO: Make it pluggable
   private final Map<Object, Object> countries = new HashMap<>();

   public CountrySpace() {
      System.out.printf("@%x%n", System.identityHashCode(this));
      countries.put("Spain", new Country("Spain", "EUR"));
   }

   @Override
   public AdvancedExternalizer<Object> externalizer() {
      return externalizer;
   }

   @Override
   public String name() {
      return "country-space";
   }

   @Override
   public void put(Object key, Object value) {
      countries.put(key, value);
   }

   @Override
   public Object get(Object key) {
      return countries.get(key);
   }

}
