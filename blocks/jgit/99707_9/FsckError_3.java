package org.eclipse.jgit.internal.fsck;

import java.io.IOException;

import org.eclipse.jgit.lib.ProgressMonitor;

public interface Fsck {

	public abstract FsckError check(ProgressMonitor pm) throws IOException;
}
