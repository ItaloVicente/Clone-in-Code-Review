	private void assertNumberOfTopLevelItems(int expectedCount) {
		int actualCount = fTreeViewer.getViewer().getTree().getItemCount();
		Assert.isTrue(actualCount == expectedCount,
				"tree item count " + actualCount + " does not match expected: " + expectedCount);
	}

	private void applyPattern(String pattern) {
		fTreeViewer.getPatternFilter().setPattern(pattern);
		fTreeViewer.getViewer().refresh();
	}

