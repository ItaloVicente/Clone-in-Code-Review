
		try {
			modelService.findElements(null, null, EModelService.ANYWHERE, null);
			fail("An exception should have prevented a null parameter to findElements(*)");
		} catch (IllegalArgumentException e) {
		}
