package sos;

import sos.pojos.CountryFactory;
import sos.pojos.Person;

/**
 * Run with:
 *
 * -Djgroups.join_timeout=1000 -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=127.0.0.1 -ea
 *
 */
public class SharedObjectSpaces {

   public static void main(String[] args) {
      Cluster.<String, Person>withCluster((c0, c1) -> {
         final CountryFactory countryFactory0 = new CountryFactory();

         Person me = new Person("me", countryFactory0.getCountry("Spain"));
         Person you = new Person("you", countryFactory0.getCountry("Spain"));

         c0.put("me", me);
         c0.put("you", you);
         // assertSame(me.nationality, you.nationality);

         Person me0 = c0.get("me");
         Person you0 = c0.get("you");
         assertEquals("Spain", me0.nationality.name);
         assertEquals("Spain", you0.nationality.name);
         // assertSame(me0.nationality, you0.nationality);

         Person me1 = c1.get("me");
         Person you1 = c1.get("you");
         assertEquals("Spain", me1.nationality.name);
         assertEquals("Spain", you1.nationality.name);
         // assertSame(me1.nationality, you1.nationality);
      });
   }

   private static void assertEquals(Object expected, Object actual) {
      assert expected.equals(actual) :
            String.format("Expected:<%s> but was:<%s>", expected, actual);
   }

   private static void assertSame(Object expected, Object actual) {
      assert expected == actual :
            String.format("Expected same:<%s> was not:<%s>", expected, actual);
   }

}
