                            Object[] currentExpanded = filteredTree.getViewer()
                                    .getExpandedElements();
                            Object[] expanded = new Object[delta.length
                                    + currentExpanded.length];
                            System.arraycopy(currentExpanded, 0, expanded, 0,
                                    currentExpanded.length);
                            System.arraycopy(delta, 0, expanded,
                                    currentExpanded.length, delta.length);
                            filteredTree.getViewer().setExpandedElements(expanded);
                        } else {
                        	filteredTree.getViewer().addFilter(filter);
                            if (projectsOnly) {
