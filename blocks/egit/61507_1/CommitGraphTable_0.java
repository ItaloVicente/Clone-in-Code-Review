				IStructuredSelection selection = (IStructuredSelection) selectionProvider
						.getSelection();
				if (selection.isEmpty()) {
					return false;
				}
				ObjectId selectedId = ((RevCommit) selection.getFirstElement())
						.getId();
