            if (window == null) {
                throw new IllegalArgumentException();
            }
            WorkbenchCommandAction action = new WorkbenchCommandAction("org.eclipse.ui.window.switchToEditor", window); //$NON-NLS-1$
            action.setId(getId());
            action.setText(WorkbenchMessages.WorkbenchEditorsAction_label);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.WORKBENCH_EDITORS_ACTION);
            return action;
        }
    };
