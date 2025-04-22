		Optional<QuickAccessContents> existingContents = Arrays.stream(window.getShell().getDisplay().getShells()) //
				.filter(Shell::isVisible) //
				.map(Shell::getData) //
				.filter(QuickAccessDialog.class::isInstance) //
				.map(QuickAccessDialog.class::cast) //
				.map(QuickAccessDialog::getQuickAccessContents) //
				.findAny();
		if (existingContents.isPresent()) {
			QuickAccessContents contents = existingContents.get();
			contents.setShowAllMatches(!contents.getShowAllMatches());
		} else {
			displayQuickAccessDialog(window, executionEvent.getCommand());
