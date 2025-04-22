package org.eclipse.egit.ui.internal.revision;

import java.net.URI;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.core.history.ITag;
import org.eclipse.team.core.history.provider.FileRevision;

public class LocalFileRevision extends FileRevision {
	private IFileState state;

	private IFile file;

	private IFileRevision baseRevision;

	public LocalFileRevision(IFileState state) {
		this.state = state;
		this.file = null;
		this.baseRevision = null;
	}

	public LocalFileRevision(IFile file) {
		this.file = file;
		this.baseRevision = null;
		this.state = null;
	}

	@Override
	public String getContentIdentifier() {
		if (file != null)
			return baseRevision == null ? NLS.bind(
					UIText.LocalFileRevision_CurrentVersion, "") : NLS.bind(UIText.LocalFileRevision_CurrentVersion, baseRevision.getContentIdentifier()); //$NON-NLS-1$
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getAuthor() {
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getComment() {
		if (file != null)
			return UIText.LocalFileRevision_currentVersionTag;
		return null;
	}

	@Override
	public ITag[] getTags() {
		return new ITag[0];
	}

	@Override
	public IStorage getStorage(IProgressMonitor monitor) throws CoreException {
		if (file != null) {
			return file;
		}
		return state;
	}

	@Override
	public String getName() {
		if (file != null) {
			return file.getName();
		}

		return state.getName();
	}

	@Override
	public long getTimestamp() {
		if (file != null) {
			return file.getLocalTimeStamp();
		}

		return state.getModificationTime();
	}

	@Override
	public boolean exists() {
		if (file != null) {
			return file.exists();
		}

		return state.exists();
	}

	public void setBaseRevision(IFileRevision baseRevision) {
		this.baseRevision = baseRevision;
	}

	@Override
	public boolean isPropertyMissing() {
		return true;
	}

	@Override
	public IFileRevision withAllProperties(IProgressMonitor monitor) {
		return this;
	}

	public boolean isPredecessorOf(IFileRevision revision) {
		long compareRevisionTime = revision.getTimestamp();
		return (this.getTimestamp() < compareRevisionTime);
	}

	public boolean isDescendentOf(IFileRevision revision) {
		long compareRevisionTime = revision.getTimestamp();
		return (this.getTimestamp() > compareRevisionTime);
	}

	@Override
	public URI getURI() {
		if (file != null)
			return file.getLocationURI();

		return URIUtil.toURI(state.getFullPath());
	}

	public IFile getFile() {
		return file;
	}

	public IFileState getState() {
		return state;
	}

	public boolean isCurrentState() {
		return file != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof LocalFileRevision) {
			LocalFileRevision other = (LocalFileRevision) obj;
			if (file != null && other.file != null)
				return file.equals(other.file);
			if (state != null && other.state != null)
				return statesEqual(state, other.state);
		}
		return false;
	}

	private boolean statesEqual(IFileState s1, IFileState s2) {
		return (s1.getFullPath().equals(s2.getFullPath()) && s1
				.getModificationTime() == s2.getModificationTime());
	}

	@Override
	public int hashCode() {
		if (file != null)
			return file.hashCode();
		if (state != null)
			return (int) state.getModificationTime();
		return super.hashCode();
	}
}
