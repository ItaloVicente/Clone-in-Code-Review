	private MPerspective findInPerspectiveStack(IPerspectiveDescriptor desc) {
		MPerspectiveStack perspectives = getPerspectiveStack();
		for (MPerspective mperspective : perspectives.getChildren()) {
			if (mperspective.getElementId().equals(desc.getId())) {
				return mperspective;
			}
		}
		return null;
	}

	private MPerspective getOrCreateModelPerspective(IPerspectiveDescriptor desc) {
		MPerspective modelPerspective = (MPerspective) modelService.cloneSnippet(application, desc.getId(), window);

		if (modelPerspective == null) {

			modelPerspective = modelService.createModelElement(MPerspective.class);

			modelPerspective.setElementId(desc.getId());

			IPerspectiveFactory factory = ((PerspectiveDescriptor) desc).createFactory();
			ModeledPageLayout modelLayout = new ModeledPageLayout(window, modelService, partService, modelPerspective,
					desc, this, true);
			factory.createInitialLayout(modelLayout);
			PerspectiveTagger.tagPerspective(modelPerspective, modelService);
			PerspectiveExtensionReader reader = new PerspectiveExtensionReader();
			reader.extendLayout(getExtensionTracker(), desc.getId(), modelLayout);
		}

		modelPerspective.setLabel(desc.getLabel());

		ImageDescriptor imageDescriptor = desc.getImageDescriptor();
		if (imageDescriptor != null) {
			String imageURL = MenuHelper.getImageUrl(imageDescriptor);
			modelPerspective.setIconURI(imageURL);
		}

		modelService.hideLocalPlaceholders(window, modelPerspective);

		return modelPerspective;
	}

	private void addPerspectiveExtrasToStack() {
		String extras = PrefUtil.getAPIPreferenceStore()
				.getString(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS);
		StringTokenizer tok = new StringTokenizer(extras, ", "); //$NON-NLS-1$
		while (tok.hasMoreTokens()) {
			String id = tok.nextToken();
			IPerspectiveDescriptor desc = getPerspectiveDesc(id);
			if (desc != null && findInPerspectiveStack(desc) == null) {
				MPerspective perspective = getOrCreateModelPerspective(desc);
				getPerspectiveStack().getChildren().add(perspective);
			}
		}

	}

