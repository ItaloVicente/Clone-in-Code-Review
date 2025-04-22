package org.eclipse.egit.ui.internal.decorators;

import org.eclipse.core.resources.IResource;

public class TestDecoratableResource extends DecoratableResource {

	private String name = null;
	private int type = -1;

	public TestDecoratableResource(IResource resource) {
		super(resource);
	}

	public TestDecoratableResource(String name, int type) {
		super(null);
		this.name = name;
		this.type = type;
	}

	public TestDecoratableResource tracked() {
		setTracked(true);
		return this;
	}

	public TestDecoratableResource ignored() {
		setIgnored(true);
		return this;
	}

	public TestDecoratableResource dirty() {
		setDirty(true);
		return this;
	}

	public TestDecoratableResource conflicts() {
		setConflicts(true);
		return this;
	}

	public TestDecoratableResource added() {
		setStagingState(StagingState.ADDED);
		return this;
	}

	public TestDecoratableResource removed() {
		setStagingState(StagingState.REMOVED);
		return this;
	}

	public TestDecoratableResource modified() {
		setStagingState(StagingState.MODIFIED);
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IDecoratableResource))
			return false;

		IDecoratableResource decoratableResource = (IDecoratableResource) obj;
		if (!(decoratableResource.getType() == getType()))
			return false;
		if (!decoratableResource.getName().equals(getName()))
			return false;
		if (!(decoratableResource.isTracked() == isTracked()))
			return false;
		if (!(decoratableResource.isIgnored() == isIgnored()))
			return false;
		if (!(decoratableResource.isDirty() == isDirty()))
			return false;
		if (!(decoratableResource.hasConflicts() == hasConflicts()))
			return false;
		if (!decoratableResource.getStagingState().equals(getStagingState()))
			return false;

		return true;
	}

	@Override
	public String getName() {
		if (name != null) {
			return name;
		}
		return super.getName();
	}

	@Override
	public int getType() {
		if (type >= 0) {
			return type;
		}
		return super.getType();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
