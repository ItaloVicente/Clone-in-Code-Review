    private class AdvancedDialog extends TrayDialog {



        ActivityEnabler enabler;
        /**
         * @param parentShell
         */
        protected AdvancedDialog(Shell parentShell) {
            super(parentShell);
			setShellStyle(getShellStyle() | SWT.SHEET);
         }

		@Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            String activityName = strings.getProperty(ACTIVITY_NAME, ActivityMessages.ActivityEnabler_activities);
            activityName = Util.replaceAll(activityName, "&", ""); //strips possible mnemonic //$NON-NLS-1$ //$NON-NLS-2$
            activityName = Util.replaceAll(activityName, ":", ""); //strips possible colon //$NON-NLS-1$ //$NON-NLS-2$
			newShell.setText(NLS.bind(
            		ActivityMessages.ActivitiesPreferencePage_advancedDialogTitle,
            		activityName
            ));
        }

		@Override
		protected Control createDialogArea(Composite parent) {
            Composite composite = (Composite) super.createDialogArea(parent);
            enabler = new ActivityEnabler(workingCopy, strings);
            Control enablerControl = enabler.createControl(composite);
            enablerControl.setLayoutData(new GridData(GridData.FILL_BOTH));
            return composite;
        }

        @Override
		protected void okPressed() {
            enabler.updateActivityStates();
            super.okPressed();
        }

    	@Override
		protected IDialogSettings getDialogBoundsSettings() {
            IDialogSettings settings = WorkbenchPlugin.getDefault().getDialogSettings();
            IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
            if (section == null) {
                section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
            }
            return section;
    	}

        @Override
		protected boolean isResizable() {
        	return true;
        }
    }
