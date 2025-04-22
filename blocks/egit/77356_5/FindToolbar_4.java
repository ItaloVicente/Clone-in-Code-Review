		String currentPattern = patternField.getText();
		if (!hadCommits && currentPattern != null
				&& !currentPattern.isEmpty()) {
			final FindToolbarJob finder = createFinder();
			finder.setUser(false);
			finder.schedule();
		}
