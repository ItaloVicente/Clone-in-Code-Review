		Builder builder = PlainMessageDialog.getBuilder(getShell(),
				WorkbenchMessages.PerspectivesPreference_perspectiveopen_title);
		builder.message(NLS.bind(WorkbenchMessages.PerspectivesPreference_perspectiveopen_message, desc.getLabel()));
		builder.image(getShell().getDisplay().getSystemImage(SWT.ICON_QUESTION));
		builder.buttonLabels(Arrays.asList(IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL));
		return builder.build().open() == 0;
