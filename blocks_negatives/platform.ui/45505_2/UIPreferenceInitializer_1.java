		rootNode
				.addNodeChangeListener(new IEclipsePreferences.INodeChangeListener() {
					/*
					 * (non-Javadoc)
					 *
					 * @see
					 * org.eclipse.core.runtime.preferences.IEclipsePreferences
					 * .INodeChangeListener
					 * #added(org.eclipse.core.runtime.preferences
					 * .IEclipsePreferences.NodeChangeEvent)
					 */
					public void added(NodeChangeEvent event) {
						if (!event.getChild().name().equals(uiName)) {
							return;
						}
						((IEclipsePreferences) event.getChild())
								.addPreferenceChangeListener(PlatformUIPreferenceListener
										.getSingleton());

					}

					/*
					 * (non-Javadoc)
					 *
					 * @see
					 * org.eclipse.core.runtime.preferences.IEclipsePreferences
					 * .INodeChangeListener
					 * #removed(org.eclipse.core.runtime.preferences
					 * .IEclipsePreferences.NodeChangeEvent)
					 */
					public void removed(NodeChangeEvent event) {

					}

				});
