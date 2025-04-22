					boolean showAll = showAllCheck.getSelection();

					if (showAll) {
						filteredTree.getViewer().getControl().setRedraw(false);
					} else {
						delta = filteredTree.getViewer().getExpandedElements();
					}

					try {
						if (showAll) {
							filteredTree.getViewer().resetFilters();
							filteredTree.getViewer().addFilter(filteredTreeFilter);
							if (projectsOnly) {
