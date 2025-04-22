	}

	public TestDecoratableResource tracked() {
		this.tracked = true;
		return this;
	}

	public TestDecoratableResource ignored() {
		this.ignored = true;
		return this;
	}

	public TestDecoratableResource dirty() {
		this.dirty = true;
		return this;
	}

	public TestDecoratableResource conflicts() {
		this.conflicts = true;
		return this;
	}

	public TestDecoratableResource added() {
		this.staged = Staged.ADDED;
		return this;
	}

	public IDecoratableResource modified() {
		this.staged = Staged.MODIFIED;
		return this;
