
package net.spy.memcached;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import junit.framework.TestCase;

public class KetamaNodeKeyFormatterTest extends TestCase {

    private MemcachedNode defaultNode = new MockMemcachedNode(new InetSocketAddress("localhost", 11211));
    private InetAddress ip;
    private MemcachedNode noHostnameNode;
    private MemcachedNode noDefaultPortNode = new MockMemcachedNode(new InetSocketAddress("localhost", 11212));

    public void setUp() throws Exception {
        ip = InetAddress.getByAddress(new byte[]{1, 2, 3, 4});
        noHostnameNode = new MockMemcachedNode(new InetSocketAddress(ip, 11211));
    }

    public void testSpymemcachedFormatIsDefault() throws Exception {
        KetamaNodeKeyFormatter formatter = new KetamaNodeKeyFormatter();
        assertEquals(formatter.getFormat(), KetamaNodeKeyFormatter.Format.SPYMEMCACHED);
    }

    public void testSpymemcachedFormat() throws Exception {
        KetamaNodeKeyFormatter formatter = new KetamaNodeKeyFormatter(KetamaNodeKeyFormatter.Format.SPYMEMCACHED);
        assertEquals("localhost/127.0.0.1:11211-1", formatter.getKeyForNode(defaultNode, 1));
        assertEquals("1.2.3.4:11211-1", formatter.getKeyForNode(noHostnameNode, 1));
        assertEquals("localhost/127.0.0.1:11212-1", formatter.getKeyForNode(noDefaultPortNode, 1));
    }

    public void testLibmemcachedFormat() throws Exception {
        KetamaNodeKeyFormatter formatter = new KetamaNodeKeyFormatter(KetamaNodeKeyFormatter.Format.LIBMEMCACHED);
        assertEquals("localhost-1", formatter.getKeyForNode(defaultNode, 1));
        assertEquals("1.2.3.4-1", formatter.getKeyForNode(noHostnameNode, 1));
        assertEquals("localhost:11212-1", formatter.getKeyForNode(noDefaultPortNode, 1));
    }
}
