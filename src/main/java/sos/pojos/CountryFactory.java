package sos.pojos;

import java.util.HashMap;
import java.util.Map;

import org.infinispan.factories.scopes.Scope;
import org.infinispan.factories.scopes.Scopes;

@Scope(Scopes.GLOBAL)
public class CountryFactory {

   private final Map<String, Country> countries = new HashMap<>();

   public CountryFactory() {
      countries.put("Spain", new Country("Spain", "EUR"));
   }

   public   Country getCountry(String name) {
      return countries.get(name);
   }

}
