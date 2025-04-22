			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_delete);
			action.setToolTipText(WorkbenchMessages.Workbench_deleteToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			action.enableAccelerator(false);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
			ISharedImages sharedImages = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
			action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
			return action;
		}
	};
