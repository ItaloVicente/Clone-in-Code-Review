
				/* BEGIN workaround for bug 319846 BEGIN */
				WorkbenchPage page = (WorkbenchPage) getSite().getPage();
				MPart part = page.findPart(this);
				if (part != null) {
					part.getContext().get(ESelectionService.class).setSelection(provider.getSelection());
				}
				/* END workaround for bug 319846 END */
