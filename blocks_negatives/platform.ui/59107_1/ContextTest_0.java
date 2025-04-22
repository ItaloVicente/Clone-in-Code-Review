	/*
	 *
	 * public void testThreeContexts() throws Exception { IEclipseContext
	 * appContext = createGlobalContext();
	 *
	 * defineCommands(appContext);
	 *
	 * EHandlerService service = (EHandlerService) appContext
	 * .get(EHandlerService.class.getName()); TestHandler handler = new
	 * TestHandler(true, HELP_COMMAND_ID);
	 * service.activateHandler(HELP_COMMAND_ID, handler);
	 *
	 * IEclipseContext window = createContext(appContext, "windowContext");
	 * appContext.set(IServiceConstants.ACTIVE_CHILD, window); EHandlerService
	 * windowService = (EHandlerService) window
	 * .get(EHandlerService.class.getName()); String windowRC = HELP_COMMAND_ID
	 * + ".window"; TestHandler windowHandler = new TestHandler(true, windowRC);
	 * windowService.activateHandler(HELP_COMMAND_ID, windowHandler);
	 * assertEquals(windowRC, service.executeHandler(HELP_COMMAND_ID));
	 *
	 * IEclipseContext dialog = createContext(appContext, "dialogContext");
	 * appContext.set(IServiceConstants.ACTIVE_CHILD, dialog);
	 * assertEquals(HELP_COMMAND_ID, service.executeHandler(HELP_COMMAND_ID));
	 *
	 * appContext.set(IServiceConstants.ACTIVE_CHILD, window);
	 * assertEquals(windowRC, service.executeHandler(HELP_COMMAND_ID)); }
	 */

