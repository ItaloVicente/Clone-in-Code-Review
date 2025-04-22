		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(windowA);
		wb.createAndRunUI(windowB);
