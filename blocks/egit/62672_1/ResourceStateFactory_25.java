package org.eclipse.egit.ui.internal.resources;

import org.eclipse.jgit.annotations.NonNull;

public class ResourceState implements IResourceState {

	private boolean tracked;

	private boolean ignored;

	private boolean dirty;

	private boolean missing;

	@NonNull
	private StagingState staged = StagingState.NOT_STAGED;

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
	public boolean isMissing() {
		return missing;
	}

	@Override
	public StagingState getStagingState() {
		return staged;
	}

	@Override
	public final boolean isStaged() {
		return staged != StagingState.NOT_STAGED;
	}

	@Override
	public boolean hasConflicts() {
		return conflicts;
	}

	@Override
	public boolean isAssumeValid() {
		return assumeValid;
	}

	protected void setStagingState(@NonNull StagingState staged) {
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

	protected void setMissing(boolean missing) {
		this.missing = missing;
	}

	protected void setAssumeValid(boolean assumeValid) {
		this.assumeValid = assumeValid;
	}

}
