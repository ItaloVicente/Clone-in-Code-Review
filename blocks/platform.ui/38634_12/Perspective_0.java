	private List<IActionSetDescriptor> createInitialActionSets(List<String> ids) {
		List<IActionSetDescriptor> result = new ArrayList<IActionSetDescriptor>();
		ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();
		for (String id : ids) {
			IActionSetDescriptor desc = reg.findActionSet(id);
			if (desc != null) {
				result.add(desc);
