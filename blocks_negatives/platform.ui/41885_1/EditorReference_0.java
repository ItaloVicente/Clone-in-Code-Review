				String memento = getModel().getPersistedState().get(MEMENTO_KEY);
				if (memento != null) {
					try {
						XMLMemento createReadRoot = XMLMemento.createReadRoot(new StringReader(
								memento));
						IMemento inputMem = createReadRoot.getChild(IWorkbenchConstants.TAG_INPUT);
						if (inputMem != null) {
							return inputMem.getString(IWorkbenchConstants.TAG_FACTORY_ID);
						}
					} catch (WorkbenchException e) {
						return null;
					}
				}
				return null;
