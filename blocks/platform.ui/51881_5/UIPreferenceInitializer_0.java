		rootNode.addNodeChangeListener(new IEclipsePreferences.INodeChangeListener() {

			@Override
			public void added(NodeChangeEvent event) {
				if (!event.getChild().name().equals(uiName)) {
					return;
				}
				((IEclipsePreferences) event.getChild())
						.addPreferenceChangeListener(PlatformUIPreferenceListener.getSingleton());

			}

			@Override
			public void removed(NodeChangeEvent event) {

			}

		});
