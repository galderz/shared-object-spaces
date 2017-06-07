package sos.pojos;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.Set;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.factories.annotations.Inject;
import org.infinispan.factories.scopes.Scope;
import org.infinispan.factories.scopes.Scopes;

public class Country {

   public final String name;
   public final String currency;

   public Country(String name, String currency) {
      this.name = name;
      this.currency = currency;
   }

   @Override
   public String toString() {
      return String.format(
            "Country{name='%s', currency='%s'}@%x",
            name, currency, System.identityHashCode(this));
   }

   @Scope(Scopes.GLOBAL)
   public static final class Externalizer implements AdvancedExternalizer<Country> {

      private CountryFactory countryFactory;

      @Inject
      public void setCountryFactory(CountryFactory countryFactory) {
         this.countryFactory = countryFactory;
      }

      @Override
      public Set<Class<? extends Country>> getTypeClasses() {
         return Collections.singleton(Country.class);
      }

      @Override
      public Integer getId() {
         return null;
      }

      @Override
      public void writeObject(ObjectOutput out, Country obj) throws IOException {
         out.writeUTF(obj.name);
         out.writeUTF(obj.currency);
      }

      @Override
      public Country readObject(ObjectInput in) throws IOException {
         String name = in.readUTF();
         String currency = in.readUTF();
         return new Country(name, currency);
         // return countryFactory.getCountry(name);
      }
   }

}
