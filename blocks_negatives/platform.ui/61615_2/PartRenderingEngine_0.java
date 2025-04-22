					MWindow selected = theApp.getSelectedElement();
					if (selected == null) {
						for (MWindow window : theApp.getChildren()) {
							createGui(window);
						}
					} else {
						createGui(selected);
						for (MWindow window : theApp.getChildren()) {
							if (selected != window) {
								createGui(window);
							}
						}
