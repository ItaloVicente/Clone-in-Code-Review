		Dictionary<String, String> props = new Hashtable<>(4);
		props.put(DebugOptions.LISTENER_SYMBOLICNAME, PLUGIN_ID);
		context.registerService(DebugOptionsListener.class.getName(), this,
				props);

		GpgSigner.setDefault(new ExternalGpgSigner());
