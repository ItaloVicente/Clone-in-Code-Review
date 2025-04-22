package org.eclipse.egit.ui.internal.staging;

import java.util.EnumSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.internal.decorators.IDecoratableResource;
import org.eclipse.egit.ui.internal.decorators.IProblemDecoratable;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;


public class StagingEntry extends PlatformObject
		implements IProblemDecoratable, IDecoratableResource {

	public static enum State {
		ADDED(EnumSet.of(Action.UNSTAGE)),

		CHANGED(EnumSet.of(Action.REPLACE_WITH_HEAD_REVISION, Action.UNSTAGE)),

		REMOVED(EnumSet.of(Action.REPLACE_WITH_HEAD_REVISION, Action.UNSTAGE)),

		MISSING(EnumSet.of(Action.REPLACE_WITH_HEAD_REVISION, Action.STAGE)),

		MISSING_AND_CHANGED(EnumSet.of(Action.REPLACE_WITH_FILE_IN_GIT_INDEX,
				Action.REPLACE_WITH_HEAD_REVISION, Action.STAGE)),

		MODIFIED(EnumSet.of(Action.REPLACE_WITH_HEAD_REVISION, Action.STAGE)),

		MODIFIED_AND_CHANGED(EnumSet.of(Action.REPLACE_WITH_FILE_IN_GIT_INDEX, Action.REPLACE_WITH_HEAD_REVISION, Action.STAGE)),

		MODIFIED_AND_ADDED(EnumSet.of(Action.REPLACE_WITH_FILE_IN_GIT_INDEX, Action.STAGE)),

		UNTRACKED(EnumSet.of(Action.STAGE, Action.DELETE, Action.IGNORE)),

		CONFLICTING(EnumSet.of(Action.REPLACE_WITH_FILE_IN_GIT_INDEX,
				Action.REPLACE_WITH_HEAD_REVISION, Action.STAGE,
				Action.LAUNCH_MERGE_TOOL, Action.REPLACE_WITH_OURS_THEIRS_MENU));

		private final Set<Action> availableActions;

		private State(Set<Action> availableActions) {
			this.availableActions = availableActions;
		}

		public Set<Action> getAvailableActions() {
			return availableActions;
		}
	}

	enum Action {
		REPLACE_WITH_FILE_IN_GIT_INDEX,
		REPLACE_WITH_HEAD_REVISION,
		STAGE,
		UNSTAGE,
		DELETE,
		IGNORE,
		LAUNCH_MERGE_TOOL,
		REPLACE_WITH_OURS_THEIRS_MENU
	}

	private final Repository repository;
	private final State state;
	private final String path;

	private boolean fileLoaded;

	private IFile file;

	private String name;

	private StagingFolderEntry parent;

	private boolean submodule;

	private boolean symlink;

	public StagingEntry(Repository repository, State state, String path) {
		this.repository = repository;
		this.state = state;
		this.path = path;
	}

	public void setSubmodule(final boolean submodule) {
		this.submodule = submodule;
	}

	public boolean isSubmodule() {
		return submodule;
	}

	public void setSymlink(boolean symlink) {
		this.symlink = symlink;
	}

	public boolean isSymlink() {
		return symlink;
	}

	public String getPath() {
		return path;
	}

	public IPath getParentPath() {
		return new Path(path).removeLastSegments(1);
	}

	public Repository getRepository() {
		return repository;
	}

	public State getState() {
		return state;
	}

	Set<Action> getAvailableActions() {
		return state.getAvailableActions();
	}

	public IFile getFile() {
		if (!fileLoaded) {
			fileLoaded = true;
			file = ResourceUtil.getFileForLocation(repository, path, false);
		}
		return file;
	}

	@NonNull
	public IPath getLocation() {
		IPath absolutePath = new Path(repository.getWorkTree().getAbsolutePath()).append(path);
		return absolutePath;
	}

	public StagingFolderEntry getParent() {
		return parent;
	}

	public void setParent(StagingFolderEntry parent) {
		this.parent = parent;
	}

	@Override
	public int getProblemSeverity() {
		IFile f = getFile();
		if (f == null)
			return SEVERITY_NONE;

		try {
			return f.findMaxProblemSeverity(IMarker.PROBLEM, true,
					IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			return SEVERITY_NONE;
		}
	}

	@Override
	public int getType() {
		return IResource.FILE;
	}

	@Override
	public String getName() {
		if (name == null) {
			IPath parsed = Path.fromOSString(getPath());
			name = parsed.lastSegment();
		}
		return name;
	}

	@Override
	public String getRepositoryName() {
		return null;
	}

	@Override
	public String getBranch() {
		return null;
	}

	@Override
	public String getBranchStatus() {
		return null;
	}

	@Override
	public String getCommitMessage() {
		return null;
	}

	@Override
	public boolean isTracked() {
		return state != State.UNTRACKED;
	}

	@Override
	public boolean isIgnored() {
		return false;
	}

	@Override
	public boolean isDirty() {
		return state == State.MODIFIED || state == State.MODIFIED_AND_CHANGED
				|| state == State.MODIFIED_AND_ADDED;
	}

	@Override
	public boolean isMissing() {
		return state == State.MISSING || state == State.MISSING_AND_CHANGED;
	}

	@Override
	public boolean hasUnstagedChanges() {
		return !isTracked() || isDirty() || isMissing() || hasConflicts();
	}

	@Override
	public StagingState getStagingState() {
		switch (state) {
		case ADDED:
			return StagingState.ADDED;
		case CHANGED:
			return StagingState.MODIFIED;
		case REMOVED:
			return StagingState.REMOVED;
		case MISSING:
		case MISSING_AND_CHANGED:
			return StagingState.REMOVED;
		default:
			return StagingState.NOT_STAGED;
		}
	}

	@Override
	public boolean isStaged() {
		return getStagingState() != StagingState.NOT_STAGED;
	}

	@Override
	public boolean hasConflicts() {
		return state == State.CONFLICTING;
	}

	@Override
	public boolean isAssumeUnchanged() {
		return false;
	}

	@Override
	public String toString() {
		return "StagingEntry[" + state + ' ' + path + ']'; //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StagingEntry other = (StagingEntry) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	@Override
	public boolean isRepositoryContainer() {
		return false;
	}
}
