		if (!clearPersistedState) {
			CommandLineOptionModelProcessor processor = ContextInjectionFactory.make(
					CommandLineOptionModelProcessor.class, context);
			processor.process();
		}

