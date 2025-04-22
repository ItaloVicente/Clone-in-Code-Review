	public ColumnWeightData(int weight, int minimumWidth, boolean resizable) {
		super(resizable);
		Assert.isTrue(weight >= 0);
		Assert.isTrue(minimumWidth >= 0);
		this.weight = weight;
		this.minimumWidth = minimumWidth;
	}
