            return action;
        }
    };

	/**
	 * Workbench action (id: "print", commandId: "org.eclipse.ui.file.print"): Print. This action is
	 * a {@link RetargetAction} with id "print". This action maintains its enablement state.
	 */
    public static final ActionFactory PRINT = new ActionFactory("print", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_PRINT) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_print);
            action.setToolTipText(WorkbenchMessages.Workbench_printToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            action
                    .setImageDescriptor(WorkbenchImages
                            .getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));
            action
                    .setDisabledImageDescriptor(WorkbenchImages
                            .getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED));
            return action;
        }
    };

	/**
	 * Workbench action (id: "properties", commandId: "org.eclipse.ui.file.properties"): Properties.
	 * This action is a {@link RetargetAction} with id "properties". This action maintains its
