package org.eclipse.egit.core.internal;

import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class CompareCoreUtils {

	public static String getResourceEncoding(Repository db, String repoPath) {
		if (db.isBare())
			return null;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkTree().getAbsolutePath())
				.append(repoPath);
		IResource resource = root.getFileForLocation(absolutePath);
		if (resource == null)
			return null;

		return getResourceEncoding(resource);
	}

	public static String getResourceEncoding(IResource resource) {
		String charset;
		IEncodedStorage encodedStorage = ((IEncodedStorage) resource);
		try {
			charset = encodedStorage.getCharset();
			if (charset == null)
				charset = resource.getParent().getDefaultCharset();
		} catch (CoreException e) {
			charset = Constants.CHARACTER_ENCODING;
		}
		return charset;
	}

}
