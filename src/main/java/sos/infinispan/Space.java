package sos.infinispan;

import org.infinispan.commons.marshall.AdvancedExternalizer;

public interface Space {

   AdvancedExternalizer externalizer();

   String name();

   void put(Object key, Object value);

   Object get(Object key);

}
