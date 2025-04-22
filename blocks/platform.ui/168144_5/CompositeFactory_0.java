

	public CompositeFactory supplyLayout(Supplier<Layout> layoutSupplier) {
		addProperty(c -> c.setLayout(layoutSupplier.get()));
		return cast(this);
	}


