		Dictionary<String, String> props = new Hashtable<>(4);
		props.put(DebugOptions.LISTENER_SYMBOLICNAME, context.getBundle()
				.getSymbolicName());
		serviceRegistration = context.registerService(
				DebugOptionsListener.class.getName(), this,
				props);
