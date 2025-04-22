					if (returnCode[0] == PlatformUI.RETURN_OK) {
						e4Workbench.createAndRunUI(e4Workbench.getApplication());
						WorkbenchMenuService wms = (WorkbenchMenuService) e4Workbench.getContext()
								.get(IMenuService.class);
						wms.dispose();
					}
