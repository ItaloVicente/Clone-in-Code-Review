package org.eclipse.egit.core.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.egit.core.Activator;
import org.eclipse.jgit.lib.FileTreeEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.team.core.TeamException;

public class GitFile extends GitResource implements IFile {

	private final FileTreeEntry fileEntry;

	public GitFile(IContainer parent, FileTreeEntry fileTreeEntry) {
		super(parent, fileTreeEntry);
		fileEntry = fileTreeEntry;
	}

	public void appendContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	public void appendContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void create(InputStream source, boolean force,
			IProgressMonitor monitor) throws CoreException {
	}

	public void create(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void createLink(IPath localLocation, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void createLink(URI location, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void delete(boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
	}

	public String getCharset() throws CoreException {
		IContentTypeManager manager = Platform.getContentTypeManager();
		try {
			IContentDescription description = manager.getDescriptionFor(
					getContents(), getName(), IContentDescription.ALL);
			return description == null ? null : description.getCharset();
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					Activator.getPluginId(), e.getMessage(), e));
		}
	}

	public String getCharset(boolean checkImplicit) throws CoreException {
		return null;
	}

	public String getCharsetFor(Reader reader) throws CoreException {
		return null;
	}

	public IContentDescription getContentDescription() throws CoreException {
		return null;
	}

	public InputStream getContents() throws CoreException {
		if (fileEntry == null)
			return null;

		try {
			return new ByteArrayInputStream(fileEntry.openReader().getBytes());
		} catch (IOException e) {
			throw new TeamException(e.getMessage(), e);
		}
	}

	public InputStream getContents(boolean force) throws CoreException {
		return getContents();
	}

	public int getEncoding() throws CoreException {
		return 0;
	}

	public IFileState[] getHistory(IProgressMonitor monitor)
			throws CoreException {
		return null;
	}

	public void move(IPath destination, boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
	}

	public void setCharset(String newCharset) throws CoreException {
	}

	public void setCharset(String newCharset, IProgressMonitor monitor)
			throws CoreException {
	}

	public void setContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	public void setContents(IFileState source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	public void setContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public void setContents(IFileState source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	public int getType() {
		return IResource.FILE;
	}

	public byte[] asBytes() {
		try {
			return fileEntry.openReader().getBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public ObjectId getObjectId() {
		return fileEntry.getId();
	}

}
