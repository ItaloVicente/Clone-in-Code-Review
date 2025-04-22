		assertEquals(66, elements.size());

		elements = modelService.findElements(application, null,
				EModelService.OUTSIDE_PERSPECTIVE,
				getSelector("InsideOutsidePerspective"));
		assertEquals(1, elements.size());
