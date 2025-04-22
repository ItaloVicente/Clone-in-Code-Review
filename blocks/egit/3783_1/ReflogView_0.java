
		reflogTableViewer.setFilters(new ViewerFilter[] { new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if ( element instanceof ReflogEntry) {
					ReflogEntry reflogEntry = (ReflogEntry) element;
					return reflogEntry.getComment().contains(searchBox.getText());
				}
				return true;
			}
		}});

