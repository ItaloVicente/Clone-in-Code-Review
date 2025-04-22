		IHandlerService handlerService = (IHandlerService) workbenchWindow
				.getService(IHandlerService.class);

        for (index = 0; index < numIterations; index++) {
            try {
                PlatformUI.getWorkbench().showPerspective(
                        ORG_ECLIPSE_JDT_UI_JAVA_PERSPECTIVE, workbenchWindow);
        		try {
        			handlerService.executeCommand(pCommand, null);
        		} catch (ExecutionException e1) {
        		} catch (NotDefinedException e1) {
        		} catch (NotEnabledException e1) {
        		} catch (NotHandledException e1) {
        		}
            } catch (WorkbenchException e) {
                e.printStackTrace();
            }
        }
    }
