		} else if (descriptor instanceof IAdaptable) {
			Object o = ((IAdaptable) descriptor).getAdapter(URL.class);
			if (o != null) {
				return rewriteDurableURL(o.toString());
			}
			o = ((IAdaptable) descriptor).getAdapter(URI.class);
			if (o != null) {
				return rewriteDurableURL(o.toString());
			}
		} else if (context != null) {
			IAdapterManager adapter = context.get(IAdapterManager.class);
			if (adapter != null) {
				Object o = adapter.getAdapter(descriptor, URL.class);
