	private String[] getCommitMsgsFromUi(final SWTBotTable table) {
		int length = table.rowCount();
		String[] result = new String[length];

		for (int i = 0; i < length; i++) {
			RevCommit commit = getCommitInLine(table, i)[0];
			String msg = commit.getFullMessage();
			result[length - (1 + i)] = msg;
		}

		return result;
	}

	private SWTBotMenu getFilterMenuItem(
			SWTBotToolbarDropDownButton selectedRefs, String refFilter) {
		return selectedRefs.menuItem(new TypeSafeMatcher<MenuItem>() {

			@Override
			public void describeTo(Description description) {
				description.appendText(
						"MenuItem for RefFilter \"" + refFilter + "\"");
			}

			@Override
			protected boolean matchesSafely(MenuItem item) {
				return item.getText().startsWith(refFilter);
			}

		});
	}

	private void verifyChecked(SWTBotToolbarDropDownButton selectedRefs,
			String refFilter) {
		SWTBotMenu filter = getFilterMenuItem(selectedRefs, refFilter);
		assertTrue("Expected " + refFilter + " to be checked",
				filter.isChecked());
	}

	private void uncheckRefFilter(SWTBotToolbarDropDownButton selectedRefs,
			String refFilter) {
		SWTBotMenu filter = getFilterMenuItem(selectedRefs, refFilter);
		assertTrue("Expected " + refFilter + " to be checked",
				filter.isChecked());
		filter.click();
	}

	private void checkRefFilter(SWTBotToolbarDropDownButton selectedRefs,
			String refFilter) {
		SWTBotMenu filter = getFilterMenuItem(selectedRefs, refFilter);
		assertTrue("Expected " + refFilter + " to be unchecked",
				!filter.isChecked());
		filter.click();
	}

	private void assertNoCommit(SWTBotTable table) {
		TestUtil.waitForJobs(50, 5000);
		Assert.assertThat("Expected no commit", getCommitMsgsFromUi(table),
				emptyArray());
	}

	private void assertCommitsAfterBase(SWTBotTable table, String... commitMsgs)
			throws Exception {
		TestUtil.waitForJobs(50, 5000);
		List<Matcher<? super String>> matchers = new ArrayList<>();
		matchers.add(equalTo("Initial commit"));
		matchers.add(startsWith("Touched at"));
		matchers.add(equalTo("A new file in a new folder"));

		for (String msg : commitMsgs) {
			matchers.add(equalTo(msg));
		}

		Assert.assertThat("Expected different commits",
				getCommitMsgsFromUi(table),
				is(arrayContainingInAnyOrder(matchers)));
	}

