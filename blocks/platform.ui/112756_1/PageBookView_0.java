	private void deactivate(PageSite pageSite) {
		if (pageSite == null) {
			reportNullPageSiteOnDeactivate(activeRec);
			return;
		}

		pageSite.deactivate();

		ISelectionProvider provider = pageSite.getSelectionProvider();
		if (provider != null) {
			provider.removeSelectionChangedListener(selectionChangedListener);
			if (provider instanceof IPostSelectionProvider) {
				((IPostSelectionProvider) provider).removePostSelectionChangedListener(postSelectionListener);
			} else {
				provider.removeSelectionChangedListener(postSelectionListener);
			}
		}
	}

	private void reportNullPageSiteOnDeactivate(PageRec pr) {
		IPage page = pr.page;
		if (page == null) {
			WorkbenchPlugin.log(new IllegalStateException("Bug 453151: page is null in PageBookView.deactivate")); //$NON-NLS-1$
		} else {
			boolean hasKey = mapPageToSite.keySet().contains(page);
			Integer count = mapPageToNumRecs.get(page);
			Control control = page.getControl();
			boolean disposed = control != null && control.isDisposed();
			String s = "Bug 453151: pageSite is null in PageBookView.deactivate, page count: " + count //$NON-NLS-1$
					+ ", key exists: " + hasKey + ", disposed: " + disposed; //$NON-NLS-1$ //$NON-NLS-2$
			WorkbenchPlugin.log(new IllegalStateException(s));
		}
	}

