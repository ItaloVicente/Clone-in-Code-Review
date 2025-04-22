		Optional<QuickAccessContents> existingContents = Arrays.stream(window.getShell().getDisplay().getShells()) //
				.filter(Shell::isVisible) //
				.map(Shell::getData) //
				.filter(QuickAccessDialog.class::isInstance) //
				.map(QuickAccessDialog.class::cast) //
				.map(QuickAccessDialog::getQuickAccessContents) //
				.findAny();
		existingContents.ifPresentOrElse( //
				contents -> contents.setShowAllMatches(!contents.getShowAllMatches()), //
				() -> displayQuickAccessDialog(window, executionEvent.getCommand()));
