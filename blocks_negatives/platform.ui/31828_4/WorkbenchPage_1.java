	/**
	 * Prompt the user to save the given saveable.
	 *
	 * @param shellProvider
	 *            the provider used to obtain a shell in prompting is required
	 * @param modelToSave
	 *            the saveable to be saved
	 * @return a list with the model to save (empty if the saveable is not
	 *         accepted to be saved) or null if canceled
	 */
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

	/**
	 * Prompt the user to save the given saveables.
	 *
	 * @param shellProvider
	 *            the provider used to obtain a shell in prompting is required
	 * @param modelsToSave
	 *            the saveables to be saved
	 * @return a list with the models selected to save or null if canceled
	 */
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
			@Override
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(monitor);
				monitorWrap.beginTask(WorkbenchMessages.Saving_Modifications, finalModels.size());
				for (Iterator i = finalModels.iterator(); i.hasNext();) {
					Saveable model = (Saveable) i.next();
					if (!model.isDirty()) {
						monitor.worked(1);
						continue;
					}
					SaveableHelper.doSaveModel(model, new SubProgressMonitor(monitorWrap, 1), shellProvider, closing
							|| confirm);
					if (monitorWrap.isCanceled()) {
						break;
					}
				}
				monitorWrap.done();
			}
		};
	}

