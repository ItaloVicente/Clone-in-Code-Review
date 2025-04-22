			BuildSetAction action = new BuildSetAction(set, window,
					actionBars);
			addMnemonic(action, accel++);
			action.setEnabled(!isAutoBuilding);
			new ActionContributionItem(action).fill(menu, -1);
		}
		if (sets.length > 0) {
