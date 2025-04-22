	public static void waitUntilViewWithGivenTitleShows(final String viewTitle) {
		waitForView(new BaseMatcher<IViewReference>() {
			public boolean matches(Object item) {
				if (item instanceof IViewReference) {
					return viewTitle.equals(((IViewReference) item).getTitle());
				}
				return false;
			}

			public void describeTo(Description description) {
				description.appendText("Wait for view with title " + viewTitle);
			}
		});
	}

