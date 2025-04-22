			if (window == null) {
				throw new IllegalArgumentException();
			}
			LabelRetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_redo);
			action.setToolTipText(WorkbenchMessages.Workbench_redoToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			ISharedImages sharedImages = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
			action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_REDO_DISABLED));
			return action;
		}
	};
