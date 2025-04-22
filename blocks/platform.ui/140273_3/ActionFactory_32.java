			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_cut);
			action.setToolTipText(WorkbenchMessages.Workbench_cutToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			ISharedImages sharedImages = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
			action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
			return action;
		}
	};

