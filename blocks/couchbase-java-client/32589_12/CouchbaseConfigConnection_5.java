
package com.couchbase.client.vbucket.provider;

import com.couchbase.client.vbucket.ConfigurationException;
import com.couchbase.client.vbucket.Reconfigurable;
import com.couchbase.client.vbucket.config.Bucket;

public interface ConfigurationProvider {

  Bucket bootstrap() throws ConfigurationException;

  Bucket getConfig();

  void setConfig(Bucket config);

  void setConfig(String config);

  void signalOutdated();

  void shutdown();

  String getAnonymousAuthBucket();

  void subscribe(Reconfigurable rec);

  void unsubscribe(Reconfigurable rec);

}
