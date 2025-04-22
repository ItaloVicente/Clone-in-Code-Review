
package com.couchbase.client.vbucket;

import com.couchbase.client.vbucket.config.Bucket;

public interface Reconfigurable {
  void reconfigure(Bucket bucket);
}
