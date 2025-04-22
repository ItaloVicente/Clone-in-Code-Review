		wb = new E4Workbench(application, context);
		wb.getContext().set(
				IStylingEngine.class,
				(IStylingEngine) Proxy.newProxyInstance(getClass()
						.getClassLoader(),
						new Class<?>[] { IStylingEngine.class },
						executedMethodsListener));

		wb.createAndRunUI(window);
		while (Display.getDefault().readAndDispatch())
			;
	}
