				Object[] unstagedExpanded = unstagedViewer
						.getExpandedElements();
				Object[] stagedExpanded = stagedViewer.getExpandedElements();
				unstagedViewer.setInput(update);
				stagedViewer.setInput(update);
				unstagedViewer.setExpandedElements(unstagedExpanded);
				stagedViewer.setExpandedElements(stagedExpanded);
