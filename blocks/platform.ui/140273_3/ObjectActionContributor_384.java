		if (objectName == null) {
			objectName = object.toString();
		}
		return SelectionEnabler.verifyNameMatch(objectName, nameFilter);
	}

	private static class ObjectContribution extends BasicContribution {
		private ObjectFilterTest filterTest;

		private ActionExpression visibilityTest;

		private Expression enablement;

		public void addFilterTest(IConfigurationElement element) {
			if (filterTest == null) {
