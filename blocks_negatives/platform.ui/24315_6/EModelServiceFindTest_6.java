		foundHandler = modelService.findHandler(null, "handler1");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(application, "");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(application, null);
		assertNull(foundHandler);
