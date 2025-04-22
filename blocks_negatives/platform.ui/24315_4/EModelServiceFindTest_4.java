		MHandler foundHandler = null;

		foundHandler = modelService.findHandler(application, "handler1");
		assertNotNull(foundHandler);
		assertSame(handler1, foundHandler);

		foundHandler = modelService.findHandler(application, "invalidId");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(null, "handler1");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(application, "");
		assertNull(foundHandler);

		foundHandler = modelService.findHandler(application, null);
		assertNull(foundHandler);
