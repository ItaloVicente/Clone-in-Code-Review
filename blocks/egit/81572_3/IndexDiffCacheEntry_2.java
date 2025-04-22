				if (repositorySupplier.get() == null) {
					ResourcesPlugin.getWorkspace()
							.removeResourceChangeListener(this);
					resourceChangeListener = null;
					return;
				}
