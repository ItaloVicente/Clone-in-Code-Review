            action.setId(getId());
			window.getWorkbench().getHelpSystem()
					.setHelp(action, IWorkbenchHelpContextIds.SAVE_ACTION);
            return action;
        }
    };
