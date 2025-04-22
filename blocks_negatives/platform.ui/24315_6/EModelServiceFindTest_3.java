		List<MUIElement> elements1 = modelService.findElements(application,
				"singleValidId", null, null);
		assertEquals(elements1.size(), 1);

		List<MUIElement> elements2 = modelService.findElements(application,
				"twoValidIds", null, null);
		assertEquals(elements2.size(), 2);

		List<MUIElement> elements3 = modelService.findElements(application,
				"invalidId", null, null);
		assertEquals(elements3.size(), 0);

		List<MUIElement> elements4 = modelService.findElements(application,
				"menuItem1Id", null, null, EModelService.ANYWHERE
						| EModelService.IN_MAIN_MENU | EModelService.IN_PART);
		assertEquals(1, elements4.size());

		List<MUIElement> elements5 = modelService.findElements(application,
				"toolControl1Id", null, null, EModelService.ANYWHERE
						| EModelService.IN_MAIN_MENU | EModelService.IN_PART);
		assertEquals(1, elements5.size());
