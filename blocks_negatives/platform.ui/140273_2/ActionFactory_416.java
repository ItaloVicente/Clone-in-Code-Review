            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_next);
            action.setToolTipText(WorkbenchMessages.Workbench_nextToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };
