
						IViewPart part = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.findView(IPageLayout.ID_PROP_SHEET);
						if (part != null) {
							PropertySheet sheet = (PropertySheet) part;
							PropertySheetPage page = (PropertySheetPage) sheet
									.getCurrentPage();
							page.refresh();
						}
