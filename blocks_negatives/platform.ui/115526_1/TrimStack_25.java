			MUIElement parentElement = changedElement.getParent();
			if (parentElement == minimizedElement) {
				trimStackTB.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						updateTrimStackItems();
					}
				});
			}
