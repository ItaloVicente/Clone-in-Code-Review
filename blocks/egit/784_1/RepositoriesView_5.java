				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						Object[] expanded = tv.getExpandedElements();
						IStructuredSelection sel = (IStructuredSelection) tv
								.getSelection();

						if (updateInput)
							tv.setInput(newInput);
						else
							tv.refresh();
						tv.setExpandedElements(expanded);

						Object selected = sel.getFirstElement();
						if (selected != null)
							tv.reveal(selected);

						IViewPart part = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.findView(IPageLayout.ID_PROP_SHEET);
						if (part != null) {
							PropertySheet sheet = (PropertySheet) part;
							PropertySheetPage page = (PropertySheetPage) sheet
									.getCurrentPage();
							page.refresh();
						}
					}
				});
