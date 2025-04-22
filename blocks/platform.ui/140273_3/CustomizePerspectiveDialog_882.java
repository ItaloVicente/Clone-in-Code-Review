					if (initSelectAS == null) {
						initSelectAS = (ActionSet) actionSetViewer.getElementAt(0);
					}
					if (initSelectAS != null) {
						setSelectionOn(actionSetViewer, initSelectAS);
						actionSetViewer.reveal(initSelectAS);
					}
					if (initSelectCI != null) {
						setSelectionOn(toolbarStructureViewer2, initSelectCI);
						toolbarStructureViewer2.reveal(initSelectCI);
					}
					book.showPage(advancedComposite);
				} else {
					book.showPage(simpleComposite);
				}
			}
		});
