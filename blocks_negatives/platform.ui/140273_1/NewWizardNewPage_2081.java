                        }
                        filteredTree.getViewer().refresh(false);

                        if (!showAll) {
                            Object[] newExpanded = filteredTree.getViewer().getExpandedElements();
                            List deltaList = new ArrayList(Arrays.asList(delta));
                            deltaList.removeAll(Arrays.asList(newExpanded));
                        }
                    } finally {
                        if (showAll) {
