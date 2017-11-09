package prototype.infinispan;

public final class Spaces {

   private Spaces() {
   }

   public static String getId(String spaceName) {
      return "prototype.infinispan.space." + spaceName;
   }

}
