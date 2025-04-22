				minMaxAddon.executeWithIgnoredTagChanges(new Runnable() {

					@Override
					public void run() {
						placeholder.getTags().remove(IPresentationEngine.MAXIMIZED);
					}
				});
