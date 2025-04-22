		MPart part = ems.createModelElement(MPart.class);
		partStack.getChildren().add(part);
		part.setLabel("some title");

		CTabItemStylingMethodsListener executedMethodsListener = new CTabItemStylingMethodsListener(part);

		context.set(IStylingEngine.class, (IStylingEngine) Proxy.newProxyInstance(getClass().getClassLoader(),
				new Class<?>[] { IStylingEngine.class }, executedMethodsListener));

		contextRule.createAndRunWorkbench(window);

