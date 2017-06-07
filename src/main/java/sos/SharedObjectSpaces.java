package sos;

import static sos.Cluster.withCluster;

/**
 * Run with:
 *
 * -Djgroups.join_timeout=1000 -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=127.0.0.1 -ea
 *
 */
public class SharedObjectSpaces {

   public static void main(String[] args) {
      withCluster((c1, c2) -> {
         c1.put("hello", "world");
         System.out.println(c2.get("hello"));
         System.out.println(c1.get("hello"));
      });
   }

}
