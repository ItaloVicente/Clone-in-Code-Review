
package net.spy.memcached;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


public class KetamaNodeKeyFormat {

    public Format getFormat() {
        return format;
    }

    public enum Format { SPYMEMCACHED, LIBMEMCACHED }
    public static Format SPYMEMCACHED = Format.SPYMEMCACHED;

    public static Format LIBMEMCACHED = Format.LIBMEMCACHED;

    private final Format format;

    private Map<MemcachedNode, String> nodeKeys = new HashMap<MemcachedNode, String>();

    public KetamaNodeKeyFormat() {
        this(SPYMEMCACHED);
    }

    public KetamaNodeKeyFormat(Format format) {
        this.format = format;
    }

    public String getKeyForNode(MemcachedNode node, int repetition) {
        String nodeKey = nodeKeys.get(node);
        if (nodeKey == null) {
            switch(this.format) {
                case LIBMEMCACHED:
                    InetSocketAddress address = (InetSocketAddress)node.getSocketAddress();
                    nodeKey = address.getHostName();
                    if (address.getPort() != 11211) {
                        nodeKey += ":" + address.getPort();
                    }
                    break;
                case SPYMEMCACHED:
                    nodeKey = String.valueOf(node.getSocketAddress());
                    if (nodeKey.startsWith("/")) {
                        nodeKey = nodeKey.substring(1);
                    }
                    System.out.println(nodeKey);
                    break;
                default:
                    assert false;
            }
            nodeKeys.put(node, nodeKey);
        }
        return nodeKey + "-" + repetition;
    }
}
