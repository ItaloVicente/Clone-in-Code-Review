
		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return project.getFile(fileName).exists();
			}
		}.waitForCondition(shell.getDisplay(), 1000);

		assertTrue("File was not created", project.getFile(fileName).exists());
		dialog.reloadCache(true, new NullProgressMonitor());

		((Text) dialog.getPatternControl()).setText(searchString);
