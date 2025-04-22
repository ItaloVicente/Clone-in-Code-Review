					selectVariable();
				}
			});
			setButtonLayoutData(variableButton);

			variableResolvedValueLabel = new Label(contents, SWT.LEAD);
			variableResolvedValueLabel.setText(resolvedValueLabelText);

			variableResolvedValueField = new Label(contents, SWT.LEAD | SWT.SINGLE | SWT.READ_ONLY);
			variableResolvedValueField.setText(TextProcessor.process(getVariableResolvedValue()));
			variableResolvedValueField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false, 2, 1));
		}
	}

	private IPathVariableManager getPathVariableManager() {
		if (currentResource != null)
			return currentResource.getPathVariableManager();
		return ResourcesPlugin.getWorkspace().getPathVariableManager();
	}

	private String getVariableResolvedValue() {
		if (currentResource != null) {
			IPathVariableManager pathVariableManager2 = currentResource.getPathVariableManager();
