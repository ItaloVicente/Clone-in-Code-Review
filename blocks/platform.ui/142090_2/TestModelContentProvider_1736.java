	protected void doNonStructureChange(TestModelChange change) {
		if (fViewer instanceof StructuredViewer) {
			((StructuredViewer) fViewer).update(change.getParent(),
					new String[] { IBasicPropertyConstants.P_TEXT });
		} else {
			Assert.isTrue(false, "Unknown kind of viewer");
		}
	}
