		PatternFilter filter = new PatternFilter() {

			@Override
			public boolean isElementVisible(Viewer viewer, Object element) {

				if (checkedItems.contains(element)) {
					return true;
				}

				return super.isElementVisible(viewer, element);
			}

		};

		FilteredTree filteredTree = new FilteredTree(listComposite, SWT.CHECK
				| SWT.BORDER, filter, true);
		filteredTree.setInitialText(UIText.WizardProjectsImportPage_filterText);
		projectsList = filteredTree.getViewer();
