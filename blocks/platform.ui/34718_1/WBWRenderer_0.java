		SelectionDialog<MPart> dialog = ListSelectionDialog.create(parentShell,
				saveableParts, MPart.class, ArrayContentProvider.getInstance(),
				new MPartLabelProvider(),
				SWTRenderersMessages.choosePartsToSaveTitle,
				SWTRenderersMessages.choosePartsToSave);
		applyDialogStyles(dialog.getShell());
