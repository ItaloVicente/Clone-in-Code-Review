		view.removePropertyListener(propertyListener);
		page.hideView(view);
		super.doTearDown();
	}

	private void verifySettings(IWorkbenchPart part, String expectedTitle,
			String expectedPartName, String expectedContentDescription)
			throws Exception {
		Assert.assertEquals("Incorrect view title", expectedTitle, part
				.getTitle());

		Assert.assertEquals("Incorrect title in view reference", expectedTitle,
				ref.getTitle());
		Assert.assertEquals("Incorrect part name in view reference",
				expectedPartName, ref.getPartName());
		Assert.assertEquals("Incorrect content description in view reference",
				expectedContentDescription, ref.getContentDescription());
	}

	private void verifySettings(String expectedTitle, String expectedPartName,
			String expectedContentDescription) throws Exception {
		verifySettings(view, expectedTitle, expectedPartName,
				expectedContentDescription);
	}

	private void verifyEvents(boolean titleEvent, boolean nameEvent,
			boolean descriptionEvent) {
		if (titleEvent) {
			Assert.assertEquals("Missing title change event", titleEvent,
					titleChangeEvent);
		}
		if (nameEvent) {
			Assert.assertEquals("Missing name change event", nameEvent,
					nameChangeEvent);
		}
		if (descriptionEvent) {
			Assert.assertEquals("Missing content description event",
					descriptionEvent, contentChangeEvent);
		}
	}

	public void testDefaults() throws Throwable {
		verifySettings("SomeTitle", "RawIViewPart", "SomeTitle");
		verifyEvents(false, false, false);
	}

	public void XXXtestCustomTitle() throws Throwable {
		view.setTitle("CustomTitle");
		verifySettings("CustomTitle", "RawIViewPart", "CustomTitle");
		verifyEvents(true, false, true);
	}

	public void XXXtestEmptyContentDescription() throws Throwable {
		view.setTitle("RawIViewPart");
		verifySettings("RawIViewPart", "RawIViewPart", "");
		verifyEvents(true, false, true);
	}
