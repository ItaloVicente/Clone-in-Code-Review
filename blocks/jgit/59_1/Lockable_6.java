
package org.eclipse.jgit.io.lock;

import java.net.URI;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public final class LockManager {

  private static LockManager lockManager;

  public static LockManager getInstance() {
    if (lockManager == null) {
      lockManager = new LockManager();
    }
    return lockManager;
  }
  private final Map<URI

  private LockManager() {
    locks = new Hashtable<URI
  }

  public synchronized ReentrantLock register(URI key) {
    if (!locks.containsKey(key)) {
      locks.put(key
              new ReentrantLock()));
    }
    return locks.get(key).get();
  }

  public synchronized void unregister(URI key) {
    if (locks.containsKey(key)) {
      LockProvider provider = locks.get(key);
      if (provider != null) {
        provider.decreateCount();
        if (provider.getRegisterCount() < 1) {
          locks.remove(key);
        }
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
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
      registerCount -= 1;
    }

    public int getRegisterCount() {
      return registerCount;
    }
  }
}
