
	private void createPerspectiveBarExtras() {
		String persps = PrefUtil.getAPIPreferenceStore()
				.getString(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS);
		String[] parts = persps.split("[, ]"); //$NON-NLS-1$
		Set<String> perspSet = new LinkedHashSet<>();
		for (String part : parts) {
			part = part.trim();
			if (!part.isEmpty())
				perspSet.add(part);
		}

		for (String perspId : perspSet) {
			MPerspective persp = (MPerspective) modelService.find(perspId, window);
			if (persp != null)
				continue; // already in stack, i.e. has already been added above
			IPerspectiveDescriptor desc = getDescriptorFor(perspId);
			if (desc == null)
				continue; // this perspective does not exist
			persp = createPerspective(desc);
			persp.setLabel(desc.getLabel());
			getPerspectiveStack().getChildren().add(persp);
		}
	}

	private IPerspectiveDescriptor getDescriptorFor(String id) {
		IPerspectiveRegistry perspectiveRegistry = getWorkbenchWindow().getWorkbench().getPerspectiveRegistry();
		if (perspectiveRegistry instanceof PerspectiveRegistry) {
			return ((PerspectiveRegistry) perspectiveRegistry).findPerspectiveWithId(id, false);
		}

		return perspectiveRegistry.findPerspectiveWithId(id);
	}

