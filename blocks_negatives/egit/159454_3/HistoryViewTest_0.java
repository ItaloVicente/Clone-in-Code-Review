		bot.waitUntil(new DefaultCondition() {

			@Override
			public boolean test() throws Exception {
				return table.rowCount() == 0;
			}

			@Override
			public String getFailureMessage() {
				return "CommitGraphTable did not become empty";
			}

		});
