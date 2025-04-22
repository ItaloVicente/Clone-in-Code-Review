		String overrideText = PrefUtil.getAPIPreferenceStore()
				.getString(IWorkbenchPreferenceConstants.HELP_SEARCH_ACTION_TEXT);
		if ("".equals(overrideText)) { //$NON-NLS-1$
			setText(WorkbenchMessages.HelpSearchAction_text);
			setToolTipText(WorkbenchMessages.HelpSearchAction_toolTip);
		} else {
			setText(overrideText);
			setToolTipText(Action.removeMnemonics(overrideText));
		}
		setImageDescriptor(WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HELP_SEARCH));
		window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.HELP_SEARCH_ACTION);
	}
