package org.eclipse.egit.ui.internal.synchronize;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkbenchPage;

public interface GitSynchronizer {

	void compare(IResource[] resources, @NonNull Repository repository,
			String leftRev, String rightRev, boolean includeLocal,
			IWorkbenchPage somePage) throws IOException;

	void compare(@NonNull IFile file, @NonNull Repository repository,
			String leftPath, String rightPath, String leftRev, String rightRev,
			boolean includeLocal, IWorkbenchPage somePage) throws IOException;
}
