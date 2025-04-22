		actionSetsViewer
				.addSelectionChangedListener(event -> {
					selectedActionSet[0] = (ActionSet) event.getStructuredSelection().getFirstElement();
					actionSetMenuViewer.setInput(menuItems);
					actionSetToolbarViewer.setInput(toolBarItems);
				});
