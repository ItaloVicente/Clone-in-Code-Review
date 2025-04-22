								updatePinActionToolbar();
							}
						});
					}
				}
			}
		};
		WorkbenchPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(propPrefListener);
		resourceListener = new IResourceChangeListener() {
