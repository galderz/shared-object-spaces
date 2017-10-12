package sos.example.pojos;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.infinispan.commons.marshall.Externalizer;
import org.infinispan.commons.marshall.SerializeWith;

@SerializeWith(Person.ExternalizerImpl.class)
public class Person {

   public final String name;
   public final Country nationality;

   public Person(String name, Country nationality) {
      this.name = name;
      this.nationality = nationality;
   }

   @Override
   public String toString() {
      return String.format(
            "Person{nationality=%s, name='%s'}@%x",
            nationality, name, System.identityHashCode(this));
   }

   public static final class ExternalizerImpl implements Externalizer<Person> {

      @Override
      public void writeObject(ObjectOutput out, Person obj) throws IOException {
         out.writeUTF(obj.name);
         out.writeObject(obj.nationality);
      }

      @Override
      public Person readObject(ObjectInput in) throws IOException, ClassNotFoundException {
         String name = in.readUTF();
         Object nationality = in.readObject();
         return new Person(name, (Country) nationality);
      }

   }

}
