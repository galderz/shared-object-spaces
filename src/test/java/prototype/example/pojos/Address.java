package prototype.example.pojos;

import org.infinispan.commons.marshall.AdvancedExternalizer;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.Set;

public class Address {

   public final String postCode;
   public final String street;
   public final int number;

   public Address(String postCode, String street, int number) {
      this.postCode = postCode;
      this.street = street;
      this.number = number;
   }

   String getId() {
      return String.format("%s/%s/%d", postCode, street, number);
   }

   @Override
   public String toString() {
      return String.format(
         "Address{postCode='%s', street='%s', number=%d}@%x",
         postCode, street, number, System.identityHashCode(this));
   }

   public static final class Externalizer implements AdvancedExternalizer<Object> {

      @Override
      public Set<Class<?>> getTypeClasses() {
         return Collections.singleton(Address.class);
      }

      @Override
      public Integer getId() {
         return 1002;
      }

      @Override
      public void writeObject(ObjectOutput output, Object object) throws IOException {
         Address address = (Address) object;
         output.writeUTF(address.getId());
      }

      @Override
      public Object readObject(ObjectInput input) throws IOException, ClassNotFoundException {
         return input.readUTF();
      }

   }

}
