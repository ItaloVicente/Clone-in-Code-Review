		IRunnableWithProgress progressOp = createRunnableWithProgress(confirm, closing, shellProvider, finalModels);

		return SaveableHelper.runProgressMonitorOperation(WorkbenchMessages.Save_All, progressOp,
				runnableContext, shellProvider);
	}

	private static void removeSaveOnCloseNotNeededParts(List parts) {
		for (Iterator it = parts.iterator(); it.hasNext();) {
			ISaveablePart saveablePart = (ISaveablePart) it.next();
			if (!saveablePart.isSaveOnCloseNeeded()) {
				it.remove();
			}
		}
	}

	private static boolean processSaveable2(List dirtyParts) {
		boolean saveable2Processed = false;
		ListIterator listIterator = dirtyParts.listIterator();

		WorkbenchPage currentPage = null;
		Perspective currentPageOriginalPerspective = null;
		while (listIterator.hasNext()) {
			IWorkbenchPart part = (IWorkbenchPart) listIterator.next();
			if (part instanceof ISaveablePart2) {
				WorkbenchPage page = (WorkbenchPage) part.getSite().getPage();
				if (!Util.equals(currentPage, page)) {
					if (currentPage != null && currentPageOriginalPerspective != null) {
						if (!currentPageOriginalPerspective.equals(currentPage
								.getActivePerspective())) {
							currentPage
									.setPerspective(currentPageOriginalPerspective.getDesc());
						}
					}
					currentPage = page;
					currentPageOriginalPerspective = page.getActivePerspective();
				}
				page.bringToTop(part);
				int choice = SaveableHelper.savePart((ISaveablePart2) part, page.getWorkbenchWindow(), true);
				if (choice == ISaveablePart2.CANCEL) {
					return true;
				} else if (choice != ISaveablePart2.DEFAULT) {
					saveable2Processed = true;
					listIterator.remove();
				}
			}
		}

		if (currentPage != null && currentPageOriginalPerspective != null) {
			if (!currentPageOriginalPerspective.equals(currentPage.getActivePerspective())) {
				currentPage.setPerspective(currentPageOriginalPerspective.getDesc());
			}
		}

		if (saveable2Processed) {
			removeNonDirtyParts(dirtyParts);
		}

		return false;
	}

	private static void removeNonDirtyParts(List parts) {
		ListIterator listIterator;
		listIterator = parts.listIterator();
		while (listIterator.hasNext()) {
			ISaveablePart part = (ISaveablePart) listIterator.next();
			if (!part.isDirty()) {
				listIterator.remove();
			}
		}
	}

	private static List promptForSaving(final IShellProvider shellProvider, Saveable modelToSave) {
		String message = NLS.bind(WorkbenchMessages.EditorManager_saveChangesQuestion,
 modelToSave.getName());
		String[] buttons = new String[] { IDialogConstants.YES_LABEL,
				IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
		MessageDialog d = new MessageDialog(shellProvider.getShell(),
				WorkbenchMessages.Save_Resource, null, message, MessageDialog.QUESTION,
				buttons, 0) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};

		int choice = SaveableHelper.testGetAutomatedResponse();
		if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
			choice = d.open();
		}
		if (choice == ISaveablePart2.CANCEL) {
			return null;
		}

		List<Saveable> modelsToSave = new ArrayList<Saveable>();
		if (choice != ISaveablePart2.NO) {
			modelsToSave.add(modelToSave);
		}
		return modelsToSave;
	}

	private static List promptForSaving(final IShellProvider shellProvider, List modelsToSave) {
		ListSelectionDialog dlg = new ListSelectionDialog(shellProvider.getShell(), modelsToSave,
				new ArrayContentProvider(), new WorkbenchPartLabelProvider(),
				WorkbenchMessages.EditorManager_saveResourcesMessage) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		dlg.setInitialSelections(modelsToSave.toArray());
		dlg.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);

		if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
			int result = dlg.open();
			if (result == IDialogConstants.CANCEL_ID) {
				return null;
			}

			modelsToSave = Arrays.asList(dlg.getResult());
		}
		return modelsToSave;
	}

	private static IRunnableWithProgress createRunnableWithProgress(final boolean confirm, final boolean closing,
			final IShellProvider shellProvider, final List finalModels) {
		return new IRunnableWithProgress() {
