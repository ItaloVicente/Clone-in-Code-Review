		viewer.getControl().getShell().getDisplay().asyncExec(() -> {
			boolean showWorkingSets = true;
			if (aMemento != null) {
				Integer showWorkingSetsInt = aMemento
						.getInteger(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS);
				showWorkingSets = showWorkingSetsInt == null || showWorkingSetsInt.intValue() == 1;
				extensionStateModel.setBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS,
						showWorkingSets);
				workingSetRootModeActionGroup.setShowTopLevelWorkingSets(showWorkingSets);

				String lastWorkingSetName = aMemento.getString(TAG_CURRENT_WORKING_SET_NAME);
				initWorkingSetFilter(lastWorkingSetName);
			} else {
				showWorkingSets = false;
