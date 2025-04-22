				@Override
				public void run() throws Exception {
					IAdaptable item = factory.createElement(itemMemento);
					if (item == null) {
						if (Policy.DEBUG_WORKING_SETS)
							WorkbenchPlugin
									.log("Unable to restore working set item - cannot instantiate item: " + factoryID); //$NON-NLS-1$
