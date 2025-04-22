				Runnable earlyStartup = new Runnable() {
					@Override
					public void run() {
						startPlugins();
						addStartupRegistryListener();
					}
