		MHandler handler1 = CommandsFactoryImpl.eINSTANCE.createHandler();
		handler1.setElementId("handler1");
		application.getHandlers().add(handler1);

		MHandler handler2 = CommandsFactoryImpl.eINSTANCE.createHandler();
		handler2.setElementId("handler2");
		application.getHandlers().add(handler2);

		MHandler foundHandler = null;

		foundHandler = modelService.findHandler(application, "handler1");
		assertNotNull(foundHandler);
		assertSame(handler1, foundHandler);
