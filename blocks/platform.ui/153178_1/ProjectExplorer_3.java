	@Override
	protected CommonViewer createCommonViewerObject(Composite aParent) {
		return new CommonViewer(getViewSite().getId(), aParent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL) {
			@Override
			protected void internalRefresh(Object element, boolean updateLabels) {
				super.internalRefresh(element, updateLabels);
				doEmptinessDetection(element);
			}

			@Override
			public void add(Object parentElement, Object... childElements) {
				super.add(parentElement, childElements);
				doEmptinessDetection(null);
			}

			@Override
			public void remove(Object... elements) {
				super.remove(elements);
				doEmptinessDetection(null);
			}

			private void doEmptinessDetection(Object element) {
				Object input = getInitialInput();
				if (element == input || element == null) {
					boolean empty = isEmpty();
					if (ProjectExplorer.this.empty != empty) {
						ProjectExplorer.this.empty = empty;
						emptyWorkspaceHelper.updateEmptiness();
					}
				}
			}
		};
	}

	private boolean isEmpty() {
		CommonViewer viewer = getCommonViewer();
		if (viewer == null) {
			return true;
		}

		INavigatorContentService navigatorContentService = viewer.getNavigatorContentService();
		Object input = getInitialInput();

		@SuppressWarnings("unchecked")
		Set<NavigatorContentExtension> rootContentExtensions = navigatorContentService.findRootContentExtensions(input);

		for (NavigatorContentExtension contentExtension : rootContentExtensions) {
			Object[] elements = contentExtension.getContentProvider().getElements(input);
			if (elements.length != 0) {
				return false;
			}
		}

		return true;
	}

