			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_print);
			action.setToolTipText(WorkbenchMessages.Workbench_printToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			action.setImageDescriptor(WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));
			action.setDisabledImageDescriptor(
					WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED));
			return action;
		}
	};
