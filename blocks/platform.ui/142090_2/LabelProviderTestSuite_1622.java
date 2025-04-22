	public LabelProviderTestSuite() {
		addTest(new LabelProviderTest("DecoratingStyledCellLabelProvider with Colors", true, true));
		addTest(new LabelProviderTest("DecoratingStyledCellLabelProvider", true, false));
		addTest(new LabelProviderTest("DecoratingLabelProvider with Colors", false, true));
		addTest(new LabelProviderTest("DecoratingLabelProvider", false, false));
	}
