						@Override
						public void run() throws Exception {
							IAdaptable item = factory
									.createElement(itemMemento);
							if (item == null) {
								if (Policy.DEBUG_WORKING_SETS)
									WorkbenchPlugin
