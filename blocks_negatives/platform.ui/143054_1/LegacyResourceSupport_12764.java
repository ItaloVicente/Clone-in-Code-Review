			Class c = LegacyResourceSupport.getDefaultContributorResourceAdapterClass();
			if (c != null) {
				try {
					Method m  = c.getDeclaredMethod("getDefault", new Class[0]);//$NON-NLS-1$
					defaultContributorResourceAdapter = m.invoke(null, new Object[0]);
					return defaultContributorResourceAdapter;
				} catch (SecurityException e) {
				} catch (NoSuchMethodException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
