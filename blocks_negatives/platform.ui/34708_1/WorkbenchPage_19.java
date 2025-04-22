		}

		MPerspective modelPerspective = (MPerspective) modelService.cloneSnippet(application,
				perspective.getId(), window);

		if (modelPerspective == null) {

			modelPerspective = modelService.createModelElement(MPerspective.class);
