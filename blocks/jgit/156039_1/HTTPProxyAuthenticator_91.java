
package org.eclipse.jgit.niofs.internal;

import org.eclipse.jgit.niofs.fs.FileSystemState;

public interface FileSystemStateAware {

    FileSystemState getState();
}
