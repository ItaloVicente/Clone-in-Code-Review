		if (bundle != null) {
			BundleContext bundleContext = bundle.getBundleContext();
			if (bundleContext != null) {
				Dictionary<String, Object> properties = new Hashtable<>();
				properties.put(EventConstants.EVENT_TOPIC, new String[] { UIEvents.UIElement.TOPIC_WIDGET });
				handlerRegistration = bundleContext.registerService(EventHandler.class.getName(),
