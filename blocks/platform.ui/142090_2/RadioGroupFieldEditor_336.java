	public RadioGroupFieldEditor(String name, String labelText, int numColumns,
			String[][] labelAndValues, Composite parent, boolean useGroup) {
		init(name, labelText);
		Assert.isTrue(checkArray(labelAndValues));
		this.labelsAndValues = labelAndValues;
		this.numColumns = numColumns;
		this.useGroup = useGroup;
		createControl(parent);
	}

	@Override
