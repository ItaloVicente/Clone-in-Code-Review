				Repository repository = getRepository();
				if (repository == null) {
					ResourcesPlugin.getWorkspace()
							.removeResourceChangeListener(this);
					resourceChangeListener = null;
					return;
				}
