				if (value && currentRepository == null) {
					if (lastSelection != null) {
						reactOnSelection(lastSelection);
					} else {
						ISelection selection = getSelectionOfActiveEditor();
						if (selection != null) {
							reactOnSelection(selection);
						}
					}
				}
