					XMLMemento createReadRoot;
					try {
						createReadRoot = XMLMemento
								.createReadRoot(new StringReader(memento));
					} catch (WorkbenchException e) {
						WorkbenchPlugin.log(e);
						descriptorId = EditorRegistry.EMPTY_EDITOR_ID;
						factoryId = null;
						return;
					}
