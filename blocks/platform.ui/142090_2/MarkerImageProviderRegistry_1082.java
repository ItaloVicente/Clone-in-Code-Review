				if (element.getName().equals(TAG_PROVIDER)) {
					addProvider(element);
					return true;
				}

				return false;
			}

			public void readRegistry() {
				readRegistry(Platform.getExtensionRegistry(),
						IDEWorkbenchPlugin.IDE_WORKBENCH,
						IDEWorkbenchPlugin.PL_MARKER_IMAGE_PROVIDER);
			}
		}

		new MarkerImageReader().readRegistry();
	}

	public void addProvider(IConfigurationElement element) {
		Descriptor desc = new Descriptor();
		desc.element = element;
