
package com.couchbase.client.vbucket;

import java.util.Observable;
import java.util.Observer;

public class BucketObserverMock implements Observer {
  private boolean updateCalled = false;

  public void update(Observable o, Object arg) {
    updateCalled = true;
  }

  public boolean isUpdateCalled() {
    return updateCalled;
  }
}
