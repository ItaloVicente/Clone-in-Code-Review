		ViewerFilter viewerFilter = new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return WizardPatternFilter.this.select(viewer, parentElement, element)
						|| hasChildren(element);
			}
			private boolean hasChildren(Object element) {
				return element instanceof WorkbenchWizardElement
						&& ((WorkbenchWizardElement) element).getCollectionElement().size() > 0;
			}
		};
