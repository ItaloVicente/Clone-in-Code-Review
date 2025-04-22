		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		boolean anyResourceSelected = !selection.isEmpty() && ResourceSelectionUtil.allResourcesAreOfType(selection,
				IResource.PROJECT | IResource.FOLDER | IResource.FILE);

		copyAction.selectionChanged(selection);
		menu.add(copyAction);
		pasteAction.selectionChanged(selection);
		menu.add(pasteAction);

		if (anyResourceSelected) {
			deleteAction.selectionChanged(selection);
			menu.add(deleteAction);
			moveAction.selectionChanged(selection);
			menu.add(moveAction);
			renameAction.selectionChanged(selection);
			menu.add(renameAction);
		}
	}

	@Override
