
		ModelAssembler mac = context.get(ModelAssembler.class);
		if (mac != null) {
			ContextInjectionFactory.invoke(mac, PostConstruct.class, context);
			mac.processModel(initialModel);
		}
