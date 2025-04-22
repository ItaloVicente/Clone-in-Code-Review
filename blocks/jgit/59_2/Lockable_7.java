
package org.eclipse.jgit.io.lock;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public final class LockManager {

  private static final LockManager lockManager;
  private final Map<URI

  static {
    lockManager = new LockManager();
  }

  public static LockManager getInstance() {
    return lockManager;
  }

  private LockManager() {
    locks = new HashMap<URI
  }

  public synchronized ReentrantLock register(URI key) {
    if (!locks.containsKey(key)) {
      locks.put(key
              new ReentrantLock()));
    }
    return locks.get(key).get();
  }

  public synchronized void unregister(URI key) {
    LockProvider provider = locks.get(key);
    if (provider != null) {
      provider.decreateCount();
      if (provider.getRegisterCount() < 1) {
        locks.remove(key);
      }
    }
  }

  private static class LockProvider {

    private int registerCount = 0;
    private ReentrantLock lock;

    public LockProvider(ReentrantLock lock) {
      this.lock = lock;
    }

    public ReentrantLock get() {
      registerCount += 1;
      return lock;
    }

    public void decreateCount() {
      registerCount -= 1;
    }

    public int getRegisterCount() {
      return registerCount;
    }
  }
}
