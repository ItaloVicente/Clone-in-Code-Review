		boolean hasMultiSelection = true;
		List<IFile> initialSelection = asList(FILES.get("foo.txt"), FILES.get("bar.txt"));

		dialog = createDialog(hasMultiSelection);
		dialog.setInitialPattern("**");
		dialog.setInitialElementSelections(initialSelection);
		dialog.open();
		dialog.refresh();
