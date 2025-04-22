					if (initSelectAS != null) {
						setSelectionOn(actionSetViewer, initSelectAS);
						actionSetViewer.reveal(initSelectAS);
					}
					if (initSelectCI != null) {
						setSelectionOn(menuStructureViewer2, initSelectCI);
						menuStructureViewer2.reveal(initSelectCI);
					}
					book.showPage(advancedComposite);
				} else {
					book.showPage(simpleComposite);
				}
			}
		});
