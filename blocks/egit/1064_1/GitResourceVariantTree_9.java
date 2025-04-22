	GitResourceVariantTree(ResourceVariantByteStore store,
			GitSynchronizeDataSet data) {
		super(store);
		this.gsds = data;
		this.store = getByteStore();

		try {
			initialize();
		} catch (Exception e) {
		}
