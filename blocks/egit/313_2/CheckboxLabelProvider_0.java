		resourceManager = new LocalResourceManager(JFaceResources.getResources());

		imageCheckedEnabled = createCheckboxImage(resourceManager, control, true, true);
		imageUncheckedEnabled = createCheckboxImage(resourceManager, control, false, true);
		imageCheckedDisabled = createCheckboxImage(resourceManager, control, true, false);
		imageUncheckedDisabled = createCheckboxImage(resourceManager, control, false, false);
