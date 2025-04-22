			try {
				((MyModel) element).counter = Integer.parseInt(value.toString());
				getViewer().update(element, null);
			} catch (Exception e) {
				MessageDialog.openError(viewer.getControl().getShell(), "Oops", "We only accept numbers..");
			}
