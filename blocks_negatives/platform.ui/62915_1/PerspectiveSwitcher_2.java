
		createPerspectiveBarExtras();
	}

	/**
	 * Add ToolItems for perspectives specified in "PERSPECTIVE_BAR_EXTRAS"
	 */
	private void createPerspectiveBarExtras() {
		String persps = PrefUtil.getAPIPreferenceStore()
				.getString(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS);
		Set<String> perspSet = new LinkedHashSet<>();
		for (String part : parts) {
			String[] parts2 = part.split(",); //$NON-NLS-1$
-			for (String part2 : parts2) {
-				part2 = part2.trim();
-				if (!part2.isEmpty())
-					perspSet.add(part2);
-			}
-		}
-
-		WorkbenchPage page = (WorkbenchPage) window.getContext().get(IWorkbenchPage.class);
-		for (String perspId : perspSet) {
-			MPerspective persp = (MPerspective) modelService.find(perspId, window);
-			if (persp != null)
-				continue; // already in stack, i.e. has already been added above
-			IPerspectiveDescriptor desc = getDescriptorFor(perspId);
-			if (desc == null)
-				continue; // this perspective does not exist
-			persp = page.createPerspective(desc);
-			persp.setLabel(desc.getLabel());
-			getPerspectiveStack().getChildren().add(persp);
-			// add" fires Event, causes creation of ToolItem on perspective bar
		}
