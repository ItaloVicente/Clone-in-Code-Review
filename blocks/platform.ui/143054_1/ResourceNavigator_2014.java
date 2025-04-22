			if (workingSet != null) {
				setTitleToolTip(NLS.bind(ResourceNavigatorMessages.ResourceNavigator_workingSetInputToolTip,
						inputToolTip, workingSet.getLabel()));
			} else {
				setTitleToolTip(inputToolTip);
			}
		}
	}

	protected ResourceNavigatorActionGroup getActionGroup() {
		return actionGroup;
	}

	protected void setActionGroup(ResourceNavigatorActionGroup actionGroup) {
		this.actionGroup = actionGroup;
	}

	@Override
