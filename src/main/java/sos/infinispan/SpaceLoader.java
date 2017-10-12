package sos.infinispan;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.factories.GlobalComponentRegistry;
import org.infinispan.lifecycle.AbstractModuleLifecycle;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

public class SpaceLoader extends AbstractModuleLifecycle {

   @Override
   public void cacheManagerStarting(GlobalComponentRegistry gcr, GlobalConfiguration global) {
      ServiceLoader<Space> srvLoader = ServiceLoader.load(Space.class);

      gcr.registerComponent(srvLoader, ServiceLoader.class);
      
      Iterator<Space> it = srvLoader.iterator();
      while (it.hasNext()) {
         Space space = it.next();
         ExternalizerWithDataContainer ext = new ExternalizerWithDataContainer(space);
         global.serialization().advancedExternalizers().put(ext.getId(), ext);
      }
   }

   private final static class ExternalizerWithDataContainer implements AdvancedExternalizer<Object> {

      private final Space space;

      private ExternalizerWithDataContainer(Space space) {
         this.space = space;
      }

      @Override
      public void writeObject(ObjectOutput objectOutput, Object o) throws IOException {
         space.externalizer().writeObject(objectOutput, o);
      }

      @Override
      public Object readObject(ObjectInput objectInput) throws IOException, ClassNotFoundException {
         Object o = space.externalizer().readObject(objectInput);
         return space.get(o);
      }

      @Override
      public Set<Class<?>> getTypeClasses() {
         return space.externalizer().getTypeClasses();
      }

      @Override
      public Integer getId() {
         return space.externalizer().getId();
      }

   }

}