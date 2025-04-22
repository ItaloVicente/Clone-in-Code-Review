			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				if (store == null) {
					super.setPreferenceStore(store);
				} else if (getPreferenceStore() == null) {
					super.setPreferenceStore(new ScopedPreferenceStore(
							InstanceScope.INSTANCE,
							org.eclipse.egit.core.Activator.getPluginId()));
				}
			}

