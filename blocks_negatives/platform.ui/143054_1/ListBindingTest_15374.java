		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				if (!status.isOK()) {
					Assert.fail("The databinding logger has the not-ok status " + status);
				}
