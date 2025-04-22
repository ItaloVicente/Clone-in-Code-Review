			contextLinesLabel = new Label(composite, SWT.NONE);
			contextLinesLabel.setText(UIText.GitCreatePatchWizard_LinesOfContext);

			contextLines = new Text(composite, SWT.BORDER | SWT.RIGHT);
			contextLines.setText("3"); //$NON-NLS-1$
			contextLines.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					validatePage();
				}
			});
			GridDataFactory.swtDefaults().hint(30, SWT.DEFAULT).applyTo(contextLines);

			updateEnablement();
