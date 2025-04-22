package org.eclipse.egit.core.internal.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.Activator;
import org.eclipse.team.core.history.IFileRevision;

public class WorkingTreeFileRevision extends GitFileRevision implements IFileRevision {

	private final File file;

	public WorkingTreeFileRevision(final File file) {
		super(file.getName());
		this.file = file;
	}

	public IStorage getStorage(IProgressMonitor monitor) throws CoreException {
		return new IStorage() {

			public Object getAdapter(Class adapter) {
				return null;
			}

			public boolean isReadOnly() {
				return true;
			}

			public String getName() {
				return file.getName();
			}

			public IPath getFullPath() {
				return new Path(file.getAbsolutePath());
			}

			public InputStream getContents() throws CoreException {
				try {
					return new FileInputStream(file);
				} catch (FileNotFoundException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				}
			}
		};
	}

	public boolean isPropertyMissing() {
		return false;
	}

	public IFileRevision withAllProperties(IProgressMonitor monitor)
			throws CoreException {
		return null;
	}

	public String getAuthor() {
		return "";  //$NON-NLS-1$
	}

	public long getTimestamp() {
		return -1;
	}

	public String getComment() {
		return "";  //$NON-NLS-1$
	}

	public String getContentIdentifier() {
		return WORKING_TREE;
	}

}
