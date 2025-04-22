
	private static class CheckBoxImages {
		private final Image checkedEnabled;

		private final Image uncheckedEnabled;

		private final Image checkedDisabled;

		private final Image uncheckedDisabled;

		public CheckBoxImages(Image checkedEnabled, Image uncheckedEnabled,
				Image checkedDisabled, Image uncheckedDisabled) {
			this.checkedEnabled = checkedEnabled;
			this.uncheckedEnabled = uncheckedEnabled;
			this.checkedDisabled = checkedDisabled;
			this.uncheckedDisabled = uncheckedDisabled;
		}

	}

	private final CheckBoxImages checkBoxes;

	private final LocalResourceManager resourceManager;

	private static CheckBoxImages createCheckboxImage(
			ResourceManager resourceManager, Control control) {
