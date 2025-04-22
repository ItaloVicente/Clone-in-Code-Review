			} else if (context != null) {
				IAdapterManager adapter = context.get(IAdapterManager.class);
				if (adapter != null) {
					Object o = adapter.getAdapter(descriptor, URL.class);
					if (o != null) {
						return rewriteDurableURL(o.toString());
					}
					o = adapter.getAdapter(descriptor, URI.class);
					if (o != null) {
						return rewriteDurableURL(o.toString());
					}
				}
