
package com.couchbase.client.java.document.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.core.message.kv.subdoc.multi.LookupCommand;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LookupSpec extends LookupCommand {

    private LookupSpec(Lookup type, String path) {
        super(type, path);
    }

    public static LookupSpec get(String path) {
        return new LookupSpec(Lookup.GET, path);
    }

    public static LookupSpec exists(String path) {
        return new LookupSpec(Lookup.EXIST, path);
    }
}
