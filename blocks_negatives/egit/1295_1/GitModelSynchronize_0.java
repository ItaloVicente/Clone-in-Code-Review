		participant.run(getTargetPart());
	}

	private static IWorkbenchPart getTargetPart() {
		IWorkbenchPart targetPart = null;
		IWorkbenchPage page = TeamUIPlugin.getActivePage();
		if (page != null) {
			targetPart = page.getActivePart();
		}
		return targetPart;
