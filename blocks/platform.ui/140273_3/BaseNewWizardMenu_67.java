		ArrayList list = new ArrayList();
		if (workbenchWindow != null && workbenchWindow.getActivePage() != null
				&& workbenchWindow.getActivePage().getPerspective() != null) {
			addItems(list);
		} else {
			String text = WorkbenchMessages.Workbench_noApplicableItems;
			Action dummyAction = new Action(text) {
			};
			dummyAction.setEnabled(false);
			list.add(new ActionContributionItem(dummyAction));
		}
		return (IContributionItem[]) list.toArray(new IContributionItem[list.size()]);
	}
