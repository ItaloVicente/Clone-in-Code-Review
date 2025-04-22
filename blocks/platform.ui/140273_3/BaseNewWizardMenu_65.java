	protected boolean addShortcuts(List list) {
		boolean added = false;
		IWorkbenchPage page = workbenchWindow.getActivePage();
		if (page != null) {
			String[] wizardIds = page.getNewWizardShortcuts();
			for (String wizardId : wizardIds) {
				IAction action = getAction(wizardId);
				if (action != null) {
					if (!WorkbenchActivityHelper.filterItem(action)) {
						list.add(new ActionContributionItem(action));
						added = true;
					}
				}
			}
		}
		return added;
	}
