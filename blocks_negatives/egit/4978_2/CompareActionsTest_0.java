	private void waitUntilCompareTreeViewShows() {
		waitForView(new BaseMatcher<IViewReference>() {
			public boolean matches(Object item) {
				if (item instanceof IViewReference) {
					return CompareTreeView.ID.equals(((IViewReference) item)
							.getId());
				}
				return false;
			}

			public void describeTo(Description description) {
				description.appendText("Wait for view with ID="
						+ CompareTreeView.ID);
			}
		});
	}

