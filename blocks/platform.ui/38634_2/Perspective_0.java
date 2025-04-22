		this.page = page;
		alwaysOnActionSets = new ArrayList<IActionSetDescriptor>(2);
		alwaysOffActionSets = new ArrayList<IActionSetDescriptor>(2);
		hideMenuIDs = new HashSet<String>();
		hideToolBarIDs = new HashSet<String>();
	}


	protected void createInitialActionSets(List<IActionSetDescriptor> outputList, List<String> stringList) {
		ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();
		for (String id : stringList) {
			IActionSetDescriptor desc = reg.findActionSet(id);
			if (desc != null) {
