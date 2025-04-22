package com.couchbase.client.java.util;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class Bootstrap {

    private static Hashtable<String, String> DNS_ENV = new Hashtable<String, String>();
    private static final String DEFAULT_DNS_FACTORY = "com.sun.jndi.dns.DnsContextFactory";
    private static final String DEFAULT_DNS_PROVIDER = "dns:";

    static {
        DNS_ENV.put("java.naming.factory.initial", DEFAULT_DNS_FACTORY);
        DNS_ENV.put("java.naming.provider.url", DEFAULT_DNS_PROVIDER);
    }

    private static final String DEFAULT_DNS_SERVICE = "_couchbase._tcp.";

    private static final String DEFAULT_DNS_SECURE_SERVICE = "_couchbases._tcp.";

    public static List<String> fromDnsSrv(final String serviceName, boolean full, boolean secure) throws NamingException {
        String fullService;
        if (full) {
            fullService = serviceName;
        } else {
            fullService = (secure ? DEFAULT_DNS_SECURE_SERVICE : DEFAULT_DNS_SERVICE) + serviceName;
        }
        DirContext ctx = new InitialDirContext(DNS_ENV);
        return loadDnsRecords(fullService, ctx);
    }

    static List<String> loadDnsRecords(final String serviceName, final DirContext ctx) throws NamingException {
        Attributes attrs = ctx.getAttributes(serviceName, new String[] { "SRV" });
        NamingEnumeration<?> servers = attrs.get("srv").getAll();
        List<String> records = new ArrayList<String>();
        while (servers.hasMore()) {
            DnsRecord record = DnsRecord.fromString((String) servers.next());
            records.add(record.getHost());
        }
        return records;
    }

    static class DnsRecord {

        private final int priority;
        private final int weight;
        private final int port;
        private final String host;

        public DnsRecord(int priority, int weight, int port, String host) {
            this.priority = priority;
            this.weight = weight;
            this.port = port;
            this.host = host.replaceAll("\\.$", "");
        }

        public String getHost() {
            return host;
        }

        public static DnsRecord fromString(String input) {
            String[] splitted = input.split(" ");
            return new DnsRecord(
                Integer.parseInt(splitted[0]),
                Integer.parseInt(splitted[1]),
                Integer.parseInt(splitted[2]),
                splitted[3]
            );
        }

        @Override
        public String toString() {
            return "DnsRecord{" +
            "priority=" + priority +
            ", weight=" + weight +
            ", port=" + port +
            ", host='" + host + '\'' +
            '}';
        }
    }


}
