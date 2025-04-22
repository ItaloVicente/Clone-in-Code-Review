			action.setText(NLS.bind(WorkbenchMessages.AboutAction_text,
					productName));
			action.setToolTipText(NLS.bind(
					WorkbenchMessages.AboutAction_toolTip, productName));
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.ABOUT_ACTION);
