						if (enterKeyDown) {
							fireOpenEvent(event);
							enterKeyDown = false;
							defaultSelectionPendent = null;
						} else {
							defaultSelectionPendent = event;
						}
