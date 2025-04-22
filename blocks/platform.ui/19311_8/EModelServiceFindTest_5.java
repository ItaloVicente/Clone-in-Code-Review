
		List<MUIElement> elements4 = modelService.findElements(application,
				"menuItem1Id", null, null);
		assertEquals(1, elements4.size());

		List<MUIElement> elements5 = modelService.findElements(application,
				"toolControl1Id", null, null);
		assertEquals(1, elements5.size());
