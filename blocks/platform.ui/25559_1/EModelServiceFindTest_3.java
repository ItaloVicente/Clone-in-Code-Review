		elements = modelService.findElements(application, null,
				EModelService.ANYWHERE | EModelService.IN_MAIN_MENU
						| EModelService.IN_PART, getSelector("menuItem1Id"));
		assertEquals(1, elements.size());

		elements = modelService.findElements(application, null,
				EModelService.ANYWHERE | EModelService.IN_MAIN_MENU
						| EModelService.IN_PART, getSelector("toolControl1Id"));
		assertEquals(1, elements.size());
