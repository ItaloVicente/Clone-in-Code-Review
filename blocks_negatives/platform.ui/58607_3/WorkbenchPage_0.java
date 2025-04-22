			modelPerspective = modelService.createModelElement(MPerspective.class);

			modelPerspective.setElementId(perspective.getId());

			IPerspectiveFactory factory = ((PerspectiveDescriptor) perspective).createFactory();
			ModeledPageLayout modelLayout = new ModeledPageLayout(window, modelService,
					partService, modelPerspective, perspective, this, true);
			factory.createInitialLayout(modelLayout);
			PerspectiveTagger.tagPerspective(modelPerspective, modelService);
			PerspectiveExtensionReader reader = new PerspectiveExtensionReader();
			reader.extendLayout(getExtensionTracker(), perspective.getId(), modelLayout);
