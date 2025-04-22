			repositoryConfig.addChangeListener(new ConfigChangedListener() {

				public void onConfigChanged(ConfigChangedEvent event) {
					repository.getListenerList().dispatch(
							new ConfigChangedEvent());
				}
			});
