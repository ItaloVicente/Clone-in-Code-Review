		ActionDescriptor desc = null;
		if (pullDownStyle) {
			desc = new ActionDescriptor(element, ActionDescriptor.T_WORKBENCH_PULLDOWN, window);
		} else {
			desc = new ActionDescriptor(element, ActionDescriptor.T_WORKBENCH, window);
		}
		WWinPluginAction action = (WWinPluginAction) desc.getAction();
		action.setActionSetId(actionSet.getDesc().getId());
		actionSet.addPluginAction(action);
		return desc;
	}

	@Override
