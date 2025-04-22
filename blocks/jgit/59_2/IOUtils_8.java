
package org.eclipse.jgit.io.lock;

import java.util.concurrent.locks.Lock;

public interface Lockable
        extends Lock {

  @Override
  public void lock()
          throws RuntimeException;

  @Override
  public void lockInterruptibly()
          throws InterruptedException

  public boolean isHeldByCurrentThread();
}
