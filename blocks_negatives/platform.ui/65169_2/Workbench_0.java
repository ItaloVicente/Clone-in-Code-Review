				for (int i = 0; i < windows.length; i++) {
					IWorkbenchPage pages[] = windows[i].getPages();
					for (int j = 0; j < pages.length; j++) {
						IViewReference[] references = pages[j].getViewReferences();
						for (int k = 0; k < references.length; k++) {
							if (references[k].getView(false) != null) {
								((ViewReference) references[k]).persist();
