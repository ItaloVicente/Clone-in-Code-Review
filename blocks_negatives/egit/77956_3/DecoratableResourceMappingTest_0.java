
class TestDecoratableResource extends DecoratableResource {

	public TestDecoratableResource(IResource resource) {
		super(resource);
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

	public IDecoratableResource modified() {
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
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "TestDecoratableResourceAdapter[" + getName() + (isTracked() ? ", tracked" : "") + (isIgnored() ? ", ignored" : "") + (isDirty() ? ", dirty" : "") + (hasConflicts() ? ",conflicts" : "") + ", staged=" + getStagingState() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$//$NON-NLS-7$//$NON-NLS-8$//$NON-NLS-9$//$NON-NLS-10$//$NON-NLS-11$
	}
}
