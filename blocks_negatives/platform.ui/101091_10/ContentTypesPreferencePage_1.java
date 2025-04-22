			}));

			editButton = new Button(buttonArea, SWT.PUSH);
			editButton.setFont(composite.getFont());
			editButton
					.setText(WorkbenchMessages.ContentTypes_fileAssociationsEditLabel);
			editButton.setEnabled(false);
			setButtonLayoutData(editButton);
			editButton.addSelectionListener(widgetSelectedAdapter(e -> {
				Shell shell = composite.getShell();
				IContentType selectedContentType = getSelectedContentType();
				Spec spec = getSelectedSpecs()[0];
				FileExtensionDialog dialog = new FileExtensionDialog(
						shell,
						WorkbenchMessages.ContentTypes_editDialog_title,
						IWorkbenchHelpContextIds.FILE_EXTENSION_DIALOG,
						WorkbenchMessages.ContentTypes_editDialog_messageHeader,
						WorkbenchMessages.ContentTypes_editDialog_message,
						WorkbenchMessages.ContentTypes_editDialog_label);
				if (spec.name == null) {
				} else {
					dialog.setInitialValue(spec.name);
