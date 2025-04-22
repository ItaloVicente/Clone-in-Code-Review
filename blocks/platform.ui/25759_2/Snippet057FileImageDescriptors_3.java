			private ImageDescriptor getDesciptorBasedOn(final Shell shell,
					String path) {
				if (registry == null) {
					registry = new ImageRegistry(shell.getDisplay());
				}
				ImageDescriptor desc = registry.getDescriptor(path);
