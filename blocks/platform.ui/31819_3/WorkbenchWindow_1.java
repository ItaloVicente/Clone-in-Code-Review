
					SaveablesList saveablesList = (SaveablesList) PlatformUI.getWorkbench()
							.getService(ISaveablesLifecycleListener.class);
					Object saveResult = saveablesList.preCloseParts(saveables, true,
							WorkbenchWindow.this);
					return saveResult != null;
