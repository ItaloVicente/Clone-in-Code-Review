
package org.eclipse.jgit.io.lock;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractLockable
        implements Lockable {

  private ReentrantLock lock;
  private boolean attainedCompleteLock;

  protected AbstractLockable() {
  }

  @Override
  protected void finalize()
          throws Throwable {
    LockManager.getInstance().unregister(getURI());
    super.finalize();
  }

  public abstract URI getURI();

  protected boolean isInternalLockHeldOnly() {
    return getLock().isHeldByCurrentThread();
  }

  public boolean isHeldByCurrentThread() {
    return isInternalLockHeldOnly() && attainedCompleteLock;
  }

  public void lock() {
    getLock().lock();
    childLock();
  }

  public void lockInterruptibly()
          throws InterruptedException {
    getLock().lockInterruptibly();
    childLock();
  }

  public Condition newCondition() {
    return getLock().newCondition();
  }

  public boolean tryLock() {
    if (isHeldByCurrentThread()) {
      return true;
    }
    final boolean tryLock = getLock().tryLock();
    if (tryLock) {
      final boolean performTryLock = performTryLock();
      if (!performTryLock) {
        unlock();
      }
      else {
        attainedCompleteLock = true;
      }
    }
    return tryLock;
  }

  public boolean tryLock(long time
                         TimeUnit unit)
          throws InterruptedException {
    if (isHeldByCurrentThread()) {
      return true;
    }
    final boolean tryLock;
    long currentTime = System.currentTimeMillis();
    tryLock = getLock().tryLock(time
    long duration = unit.convert(System.currentTimeMillis() - currentTime
            TimeUnit.MILLISECONDS);
    if (tryLock) {
      final boolean performTryLock = performTryLock(duration
      if (!performTryLock) {
        unlock();
      }
      else {
        attainedCompleteLock = true;
      }
      return performTryLock;
    }
    return tryLock;
  }

  public void unlock() {
    if (isInternalLockHeldOnly()) {
      if (attainedCompleteLock) {
        performUnlock();
      }
      attainedCompleteLock = false;
      getLock().unlock();
    }
  }

  protected ReentrantLock getLock() {
    if (lock == null) {
      lock = LockManager.getInstance().register(getURI());
    }
    return lock;
  }

  protected abstract void performLock()
          throws Exception;

  protected abstract boolean performTryLock(long time
                                            TimeUnit unit);

  protected abstract boolean performTryLock();

  protected abstract void performUnlock();

  private void childLock() {
    try {
      performLock();
      attainedCompleteLock = true;
    }
    catch (Exception ex) {
      unlock();
      attainedCompleteLock = false;
      throw new RuntimeException("Could not attain lock!"
    }
  }
}
