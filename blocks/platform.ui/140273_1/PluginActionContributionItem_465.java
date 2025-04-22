		if (identifier == null) {
			IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI.getWorkbench().getActivitySupport();
			IPluginContribution contribution = (IPluginContribution) getAction();
			identifier = workbenchActivitySupport.getActivityManager()
					.getIdentifier(WorkbenchActivityHelper.createUnifiedId(contribution));
		}
		return identifier;
	}

	private void disposeIdentifier() {
		identifier = null;
	}

	@Override
