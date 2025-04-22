					e4Workbench.createAndRunUI(e4Workbench.getApplication());
					IMenuService wms = e4Workbench.getContext().get(IMenuService.class);
					wms.dispose();
				}
				if (returnCode[0] != PlatformUI.RETURN_UNSTARTABLE) {
					setSearchContribution(appModel, false);
					e4app.saveModel();
				}
