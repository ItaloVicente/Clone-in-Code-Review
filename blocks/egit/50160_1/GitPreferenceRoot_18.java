		Group bashGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		bashGroup.setText(UIText.GitPreferenceRoot_BashGroupHeader);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(bashGroup);
		FileFieldEditor bashPathEditor = new FileFieldEditor(
				UIPreferences.BASH_PATH, UIText.GitPreferenceRoot_BashPathLabel,
				bashGroup) {

			private static final int NUMBER_OF_OWN_CONTROLS = 1;

			@Override
			protected boolean doCheckState() {
				String fileName = getTextControl().getText();
				fileName = fileName.trim();
				if (fileName.length() == 0 && isEmptyStringAllowed())
					return true;
				IStringVariableManager manager = VariablesPlugin.getDefault()
						.getStringVariableManager();
				String substitutedFileName;
				try {
					substitutedFileName = manager
							.performStringSubstitution(fileName);
				} catch (CoreException e) {
					return false;
				}
				File file = new File(substitutedFileName);
				return file.exists() || !file.isDirectory();
			}

			@Override
			public int getNumberOfControls() {
				return super.getNumberOfControls() + NUMBER_OF_OWN_CONTROLS;
			}

			@Override
			protected void doFillIntoGrid(Composite parent, int numColumns) {
				super.doFillIntoGrid(parent,
						numColumns - NUMBER_OF_OWN_CONTROLS);
			}

			@Override
			protected void adjustForNumColumns(int numColumns) {
				super.adjustForNumColumns(numColumns - NUMBER_OF_OWN_CONTROLS);
			}

			@Override
			protected void createControl(Composite parent) {
				super.setValidateStrategy(
						StringFieldEditor.VALIDATE_ON_KEY_STROKE);
				super.createControl(parent);
				if (HAS_DEBUG_UI)
					addVariablesButton(parent);
			}

			private void addVariablesButton(Composite parent) {
				Button variableButton = new Button(parent, SWT.PUSH);
				variableButton.setText(
						UIText.GitPreferenceRoot_BashPathVariableButton);
				variableButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						org.eclipse.debug.ui.StringVariableSelectionDialog dialog = new org.eclipse.debug.ui.StringVariableSelectionDialog(
								getShell());
						int returnCode = dialog.open();
						if (returnCode == Window.OK)
							setStringValue(dialog.getVariableExpression());
					}
				});
			}
		};
		updateMargins(bashGroup);
		bashPathEditor.setEmptyStringAllowed(false);
		bashPathEditor.getLabelControl(bashGroup)
				.setToolTipText(UIText.GitPreferenceRoot_BashPathTooltip);
		addField(bashPathEditor);

