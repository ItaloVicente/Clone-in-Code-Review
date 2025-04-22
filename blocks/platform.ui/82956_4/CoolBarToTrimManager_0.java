
					final List<MToolBarElement> toolbarElements = child.getChildren();
					for (int j = 0; j < toolbarElements.size(); j++) {
						final MToolBarElement toolBarElement = toolbarElements.get(j);
						IContributionItem toolbarElementItem = renderer.getContribution(toolBarElement);
						renderer.clearModelToContribution(toolBarElement, toolbarElementItem);
