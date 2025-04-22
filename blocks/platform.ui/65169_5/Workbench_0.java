				for (IWorkbenchWindow window : windows) {
					IWorkbenchPage pages[] = window.getPages();
					for (IWorkbenchPage page : pages) {
						IViewReference[] references = page.getViewReferences();
						for (IViewReference reference : references) {
							if (reference.getView(false) != null) {
								((ViewReference) reference).persist();
