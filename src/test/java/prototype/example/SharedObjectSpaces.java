package prototype.example;

import prototype.example.pojos.Address;
import prototype.example.pojos.AddressSpace;
import prototype.example.pojos.Country;
import prototype.example.pojos.CountrySpace;
import prototype.example.pojos.Person;
import prototype.infinispan.Space;
import prototype.infinispan.Spaces;

/**
 * Run with:
 *
 * -Djgroups.join_timeout=1000 -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=127.0.0.1 -ea
 *
 */
public class SharedObjectSpaces {

   public static void main(String[] args) {
      Cluster.<String, Person>withCluster((c0, c1) -> {
         Space<String, Country> countrySpace =
            c0.getCacheManager().getGlobalComponentRegistry()
               .getComponent(Spaces.getId(CountrySpace.NAME));

         Space<String, Address> addressSpace =
            c0.getCacheManager().getGlobalComponentRegistry()
               .getComponent(Spaces.getId(AddressSpace.NAME));

         Address address = addressSpace.get("48990/My Street/14");
         Country country = countrySpace.get("Spain");

         Person me = new Person("me", address, country);
         Person you = new Person("you", address, country);

         c0.put("me", me);
         c0.put("you", you);
         assertSame(me.address, you.address);
         assertSame(me.nationality, you.nationality);

         Person me0 = c0.get("me");
         Person you0 = c0.get("you");
         assertEquals("48990", me0.address.postCode);
         assertEquals("48990", you0.address.postCode);
         assertEquals("Spain", me0.nationality.name);
         assertEquals("Spain", you0.nationality.name);
         assertSame(me0.address, you0.address);
         assertSame(me0.nationality, you0.nationality);

         Person me1 = c1.get("me");
         Person you1 = c1.get("you");
         assertEquals("48990", me1.address.postCode);
         assertEquals("48990", you1.address.postCode);
         assertEquals("Spain", me1.nationality.name);
         assertEquals("Spain", you1.nationality.name);
         assertSame(me1.address, you1.address);
         assertSame(me1.nationality, you1.nationality);
      });
   }

   private static void assertEquals(Object expected, Object actual) {
      System.out.printf("assertEquals(%s, %s)%n", expected, actual);
      assert expected.equals(actual) :
            String.format("Expected:<%s> but was:<%s>", expected, actual);
   }

   private static void assertSame(Object expected, Object actual) {
      System.out.printf("assertSame(%s, %s)%n", expected, actual);
      assert expected == actual :
            String.format("Expected same:<%s> was not:<%s>", expected, actual);
   }

}
