	public static void waitUntilViewWithGivenIdShows(final String viewId) {
		waitForView(new BaseMatcher<IViewReference>() {
			public boolean matches(Object item) {
				if (item instanceof IViewReference) {
					return viewId.equals(((IViewReference) item).getId());
				}
				return false;
			}

			public void describeTo(Description description) {
				description.appendText("Wait for view with ID=" + viewId);
			}
		});
	}

