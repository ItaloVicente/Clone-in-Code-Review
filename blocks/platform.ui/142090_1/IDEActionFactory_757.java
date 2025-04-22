			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetActionWithDefault(getId(),
					IDEWorkbenchMessages.Workbench_buildProject);
			action.setToolTipText(IDEWorkbenchMessages.Workbench_buildProjectToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

