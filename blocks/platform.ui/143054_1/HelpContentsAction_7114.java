		String overrideText = PrefUtil.getAPIPreferenceStore()
				.getString(IWorkbenchPreferenceConstants.HELP_CONTENTS_ACTION_TEXT);
		if ("".equals(overrideText)) { //$NON-NLS-1$
			setText(WorkbenchMessages.HelpContentsAction_text);
			setToolTipText(WorkbenchMessages.HelpContentsAction_toolTip);
		} else {
			setText(overrideText);
			setToolTipText(Action.removeMnemonics(overrideText));
		}
		setImageDescriptor(WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HELP_CONTENTS));
		window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.HELP_CONTENTS_ACTION);
	}
