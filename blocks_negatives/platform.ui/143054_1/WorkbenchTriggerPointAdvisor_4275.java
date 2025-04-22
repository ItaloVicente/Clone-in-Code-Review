        EnablementDialog dialog = new EnablementDialog(Util.getShellToParentOn(), identifier
                .getActivityIds(), strings);
        if (dialog.open() == Window.OK) {
            Set activities = dialog.getActivitiesToEnable();
            if (dialog.getDontAsk()) {
				PrefUtil.getInternalPreferenceStore().setValue(
						IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT,
