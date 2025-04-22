
package org.eclipse.jgit.niofs.internal;

public interface LockableFileSystem {

    void lock();

    void unlock();
}
