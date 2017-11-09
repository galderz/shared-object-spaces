package prototype.example.pojos;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.container.DataContainer;
import org.infinispan.factories.GlobalComponentRegistry;
import prototype.infinispan.Space;

public class AddressSpace implements Space<String, Address> {

   public static final String NAME = "AddressSpace";

   private AdvancedExternalizer<Object> externalizer =
         new Address.Externalizer();

   private DataContainer<String, Address> addresses;

   public AddressSpace() {
      System.out.printf("AddressSpace@%x%n", System.identityHashCode(this));
   }

   @Override
   public AdvancedExternalizer externalizer() {
      return externalizer;
   }

   @Override
   public Address get(String key) {
      return addresses.get(key).getValue();
   }

   @Override
   public String name() {
      return NAME;
   }

   @Override
   public long size() {
      return -1;
   }

   @Override
   public void init(GlobalComponentRegistry cr, DataContainer<String, Address> container) {
      addresses = container;
      addresses.put("48990/My Street/14",
         new Address("48990", "My Street", 14), null);
   }

}
