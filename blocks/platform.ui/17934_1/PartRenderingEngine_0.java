	private void populateContext(MUIElement element, MContext ctxt,
			IEclipseContext lclContext) {
		populateModelInterfaces(ctxt, lclContext, element.getClass()
				.getInterfaces());
		ctxt.setContext(lclContext);


		for (String variable : ctxt.getVariables()) {
			lclContext.declareModifiable(variable);
		}

		Map<String, String> props = ctxt.getProperties();
		for (String key : props.keySet()) {
			lclContext.set(key, props.get(key));
		}

		lclContext.set(getClass().getName(), "INIT"); //$NON-NLS-1$
	}

