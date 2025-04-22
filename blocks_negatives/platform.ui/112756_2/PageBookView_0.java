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
