					.addSelectionChangedListener(event -> {
						IStructuredSelection selection = (IStructuredSelection) event
								.getSelection();
						if (selection.isEmpty()) {
							editButton.setEnabled(false);
							removeButton.setEnabled(false);
							return;
						}
						boolean enabled = true;
						List elements = selection.toList();
						for (Iterator i = elements.iterator(); i.hasNext();) {
							Spec spec = (Spec) i.next();
							if (spec.isPredefined) {
								enabled = false;
