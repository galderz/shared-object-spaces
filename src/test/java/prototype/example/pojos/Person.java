package prototype.example.pojos;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;

// Could just as well been marshalled with AdvancedExternalizer but would require pre-registration.
// @SerializeWith was simply used for ease of use.
@SerializeWith(Person.ExternalizerImpl.class)
public class Person {

   public final String name;
   public final Address address;
   public final Country nationality;

   public Person(String name, Address address, Country nationality) {
      this.name = name;
      this.address = address;
      this.nationality = nationality;
   }

   @Override
   public String toString() {
      return String.format(
            "Person{nationality=%s, address='%s', name='%s'}@%x",
            nationality, address, name, System.identityHashCode(this));
   }

   public static final class ExternalizerImpl implements Externalizer<Person> {

      @Override
      public void writeObject(ObjectOutput out, Person obj) throws IOException {
         out.writeUTF(obj.name);
         out.writeObject(obj.nationality);
         out.writeObject(obj.address);
      }

      @Override
      public Person readObject(ObjectInput in) throws IOException, ClassNotFoundException {
         String name = in.readUTF();
         Object nationality = in.readObject();
         Object address = in.readObject();
         return new Person(name, (Address) address, (Country) nationality);
      }

   }

}
