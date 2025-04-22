		noteMessage = new StyledText(noteComposite, SWT.WRAP);
		noteMessage.setEditable(false);
		noteMessage.setEnabled(autoSaveButton.getSelection());
		noteMessage.setText(IDEWorkbenchMessages.AutoSavePreferencPage_noteLabel + " " //$NON-NLS-1$
				+ IDEWorkbenchMessages.AutoSavePreferencPage_noteMessage);
		final TextStyle boldStyle = new TextStyle();
		boldStyle.font = JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT);
		final StyleRange range = new StyleRange(boldStyle);
		range.length = IDEWorkbenchMessages.AutoSavePreferencPage_noteLabel.length();
		noteMessage.setStyleRange(range);
		noteMessage.setBackgroundImage(
				IDEWorkbenchPlugin.getIDEImageDescriptor("obj16/transparent_pixel.png").createImage()); //$NON-NLS-1$

		noteMessage.setJustify(true);
