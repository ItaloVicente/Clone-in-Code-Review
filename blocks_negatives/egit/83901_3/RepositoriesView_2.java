				PlatformUI.getWorkbench().getDisplay()
						.syncExec(new Runnable() {
					@Override
					public void run() {
						if (!UIUtils.isUsable(tv)) {
							return;
						}
						long start = 0;
						if (trace) {
							start = System.currentTimeMillis();
						}

						if (needsNewInput) {
							Object[] expanded = tv.getExpandedElements();
							tv.setInput(ResourcesPlugin.getWorkspace()
									.getRoot());
							tv.setExpandedElements(expanded);
						} else {
							tv.refresh(true);
						}

						IViewPart part = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.findView(IPageLayout.ID_PROP_SHEET);
						if (part instanceof PropertySheet) {
							PropertySheet sheet = (PropertySheet) part;
							IPage page = sheet.getCurrentPage();
							if (page instanceof PropertySheetPage) {
								((PropertySheetPage) page).refresh();
							}
						}
						if (trace) {
									+ (System.currentTimeMillis() - start)
						}
						if (!repositories.isEmpty()) {
							layout.topControl = getCommonViewer().getControl();
						} else {
							layout.topControl = emptyArea;
						}
						emptyArea.getParent().layout(true, true);
					}
				});
