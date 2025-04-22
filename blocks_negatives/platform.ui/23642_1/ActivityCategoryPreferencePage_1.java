        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            String activityName = strings.getProperty(ACTIVITY_NAME, ActivityMessages.ActivityEnabler_activities);
            activityName = Util.replaceAll(activityName, "&", ""); //strips possible mnemonic //$NON-NLS-1$ //$NON-NLS-2$
			newShell.setText(NLS.bind(           		
            		ActivityMessages.ActivitiesPreferencePage_advancedDialogTitle,
            		activityName		
            ));
        }
