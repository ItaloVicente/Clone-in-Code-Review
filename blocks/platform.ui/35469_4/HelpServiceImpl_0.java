
	@Override
	public void setHelp(Object helpTarget, String helpContextId) {
		if (helpTarget instanceof Control) {
			WorkbenchHelpSystem.getInstance().setHelp((Control) helpTarget, helpContextId);
		} else if (helpTarget instanceof IAction) {
			WorkbenchHelpSystem.getInstance().setHelp((IAction) helpTarget, helpContextId);
		} else if (helpTarget instanceof Menu) {
			WorkbenchHelpSystem.getInstance().setHelp((Menu) helpTarget, helpContextId);
		} else if (helpTarget instanceof MenuItem) {
			WorkbenchHelpSystem.getInstance().setHelp((MenuItem) helpTarget, helpContextId);
		}

	}
