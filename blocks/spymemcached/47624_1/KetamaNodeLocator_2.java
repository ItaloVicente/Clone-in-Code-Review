
package net.spy.memcached;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


public enum KetamaNodeKeyFormat {

    SPYMEMCACHED,

    LIBMEMCACHED;

    private Map<MemcachedNode, String> nodeKeys = new HashMap<MemcachedNode, String>();

    public String getKeyForNode(MemcachedNode node, int repetition) {
        String nodeKey = nodeKeys.get(node);
        if (nodeKey == null) {
            switch(this) {
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
                    break;
                default:
                    assert false;
            }
            nodeKeys.put(node, nodeKey);
        }
        return nodeKey + "-" + repetition;
    }
}
