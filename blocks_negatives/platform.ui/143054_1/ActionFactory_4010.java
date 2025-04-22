			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.ImportResourcesAction_text);
            action.setToolTipText(WorkbenchMessages.ImportResourcesAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.IMPORT_ACTION);
            action.setImageDescriptor(WorkbenchImages
                    .getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ));
            return action;

        }
    };

	/**
	 * Workbench action (id: "lockToolBar"): Lock/unlock the workbench window
	 * tool bar. This action maintains its enablement state.
