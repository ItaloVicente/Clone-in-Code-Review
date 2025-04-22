
		ModelAssembler mac = context.get(ModelAssembler.class);
		if (mac != null) {
			context.set("initial", initialModel); //$NON-NLS-1$
			ContextInjectionFactory.invoke(mac, Execute.class, context);
		}
