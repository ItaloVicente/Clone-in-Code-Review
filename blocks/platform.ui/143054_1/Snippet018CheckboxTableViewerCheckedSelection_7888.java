			removePersonButton.addListener(SWT.Selection, event -> {
				IStructuredSelection selected = peopleViewer.getStructuredSelection();
				if (selected.isEmpty())
					return;
				Person person = (Person) selected.getFirstElement();
				if (MessageDialog.openConfirm(shell, "Remove person", "Remove " + person.getName() + "?"))
					people.remove(person);
