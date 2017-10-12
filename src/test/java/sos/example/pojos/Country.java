package sos.example.pojos;

import org.infinispan.commons.marshall.AdvancedExternalizer;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.Set;

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

   public static final class Externalizer implements AdvancedExternalizer<Object> {

      @Override
      public Set<Class<?>> getTypeClasses() {
         return Collections.singleton(Country.class);
      }

      @Override
      public Integer getId() {
         return 1001;
      }

      @Override
      public void writeObject(ObjectOutput out, Object obj) throws IOException {
         out.writeUTF(((Country) obj).name);
      }

      @Override
      public String readObject(ObjectInput in) throws IOException {
         return in.readUTF();
      }

   }

}
