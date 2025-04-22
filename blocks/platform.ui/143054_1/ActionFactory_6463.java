			if (window == null) {
				throw new IllegalArgumentException();
			}

			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.ExportResourcesAction_fileMenuText);
			action.setToolTipText(WorkbenchMessages.ExportResourcesAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.EXPORT_ACTION);
			action.setImageDescriptor(
					WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));
			return action;
		}
