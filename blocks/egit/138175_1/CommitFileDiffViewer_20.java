				() -> {
					IStructuredSelection selection = getStructuredSelection();
					if (selection != null && !selection.isEmpty()) {
						doCopy(selection.iterator());
					}
				});
