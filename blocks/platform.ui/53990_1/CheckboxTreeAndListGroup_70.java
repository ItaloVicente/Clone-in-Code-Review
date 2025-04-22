                () -> {
				    if (event.getCheckable().equals(treeViewer)) {
						treeItemChecked(event.getElement(), event
				                .getChecked());
					} else {
						listItemChecked(event.getElement(), event
				                .getChecked(), true);
					}
