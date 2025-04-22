
		workbench.getHelpSystem().setHelp(parent, IWorkbenchHelpContextIds.CAPABILITY_PREFERENCE_PAGE);

		if (allowAdvanced) {
			enabler = new ActivityEnabler(workingCopy, strings);
			Control enablerControl = enabler.createControl(composite);
			enablerControl.setLayoutData(new GridData(GridData.FILL_BOTH));
			return composite;
		}

