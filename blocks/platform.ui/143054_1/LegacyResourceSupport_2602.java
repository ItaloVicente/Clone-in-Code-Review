
			Method m = getContributorResourceAdapterGetAdaptedResourceMethod();
			if (m != null) {
				try {
					return m.invoke(resourceAdapter, adaptable);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
