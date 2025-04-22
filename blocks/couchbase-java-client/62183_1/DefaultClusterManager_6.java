
package com.couchbase.client.java;

import static com.couchbase.client.java.AuthenticationContext.BUCKET_KEYVALUE;
import static com.couchbase.client.java.AuthenticationContext.BUCKET_N1QL;
import static com.couchbase.client.java.AuthenticationContext.CLUSTER_FTS;
import static com.couchbase.client.java.AuthenticationContext.CLUSTER_MANAGEMENT;
import static com.couchbase.client.java.AuthenticationContext.CLUSTER_N1QL;

import java.util.HashMap;
import java.util.Map;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;

public class CredentialsManager {

    private final Map<String, String> bucketCredentials = new HashMap<String, String>();

    private Tuple2<String, String> clusterCredentials = Tuple.create(null, null);

    public CredentialsManager addBucketCredential(String bucket, String password) {
        bucketCredentials.put(bucket, password);
        return this;
    }

    public CredentialsManager addClusterCredentials(String login, String password) {
        clusterCredentials = Tuple.create(login, password);
        return this;
    }

    public String[][] getCredentials(AuthenticationContext context, String specific) {
        if (context == BUCKET_KEYVALUE || context == BUCKET_N1QL) {
            String[] cred = new String[2];
            cred[0] = specific;
            cred[1] = bucketCredentials.get(specific);
            return new String[][]{cred};
        } else if (context == CLUSTER_N1QL || context == CLUSTER_FTS) {
            String[][] creds = new String[bucketCredentials.size()][];
            int i = 0;
            for (Map.Entry<String, String> entry : bucketCredentials.entrySet()) {
                String[] cred = new String[2];
                cred[0] = entry.getKey();
                cred[1] = entry.getValue();
                creds[i++] = cred;
            }
            return creds;
        } else if (context == CLUSTER_MANAGEMENT) {
            String[] cred = new String[2];
            cred[0] = clusterCredentials.value1();
            cred[1] = clusterCredentials.value2();
            return new String[][] { cred };
        }
        throw new UnsupportedOperationException("Authentication context " + context + " is currently unsupported");
    }

}
