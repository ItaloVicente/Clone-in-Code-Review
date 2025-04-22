package org.eclipse.egit.ui.internal.resources;

import org.eclipse.jgit.annotations.NonNull;

public class ResourceState implements IResourceState {

	private boolean tracked;

	private boolean ignored;

	private boolean dirty;

	private Staged staged = Staged.NOT_STAGED;

	private boolean conflicts;

	private boolean assumeValid;

	@Override
	public boolean isTracked() {
		return tracked;
	}

	@Override
	public boolean isIgnored() {
		return ignored;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public Staged staged() {
		return staged;
	}

	@Override
	public boolean hasConflicts() {
		return conflicts;
	}

	@Override
	public boolean isAssumeValid() {
		return assumeValid;
	}

	protected void setStaged(@NonNull Staged staged) {
		this.staged = staged;
	}

	protected void setConflicts(boolean conflicts) {
		this.conflicts = conflicts;
	}

	protected void setTracked(boolean tracked) {
		this.tracked = tracked;
	}

	protected void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	protected void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	protected void setAssumeValid(boolean assumeValid) {
		this.assumeValid = assumeValid;
	}

}
