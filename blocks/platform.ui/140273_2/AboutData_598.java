	protected AboutData(String providerName, String name, String version, String id) {
		this.providerName = providerName == null ? "" : providerName; //$NON-NLS-1$
		this.name = name == null ? "" : name; //$NON-NLS-1$
		this.version = version == null ? "" : version; //$NON-NLS-1$
		this.id = id == null ? "" : id; //$NON-NLS-1$
	}
