
package com.couchbase.client.vbucket;

import java.util.Observable;
import java.util.Observer;

import com.couchbase.client.vbucket.config.Bucket;

public class ReconfigurableObserver implements Observer {
  private final Reconfigurable rec;

  public ReconfigurableObserver(Reconfigurable rec) {
    this.rec = rec;
  }

  public void update(Observable o, Object arg) {
    rec.reconfigure((Bucket) arg);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReconfigurableObserver that = (ReconfigurableObserver) o;

    if (!rec.equals(that.rec)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return rec.hashCode();
  }
}
