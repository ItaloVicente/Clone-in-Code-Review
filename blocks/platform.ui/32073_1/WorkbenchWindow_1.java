						SaveablesList saveablesList = (SaveablesList) PlatformUI.getWorkbench()
								.getService(ISaveablesLifecycleListener.class);
						Object saveResult = saveablesList.preCloseParts(
								Collections.singletonList((ISaveablePart) workbenchPart), true,
								WorkbenchWindow.this);
						return saveResult != null;
