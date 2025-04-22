		boolean hasMultiSelection = true;

		dialog = createDialog(hasMultiSelection);
		dialog.setInitialPattern("*.txt");
		dialog.setInitialElementSelections(asList(FILES.get("foo.txt"), FILES.get("bar.txt"), FILES.get("foofoo")));
		dialog.open();
		dialog.refresh();
