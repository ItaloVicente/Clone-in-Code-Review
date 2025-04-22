				.addSelectionChangedListener(event -> {
					IStructuredSelection sele = ((IStructuredSelection) tableViewer.getSelection());
					boolean sel = sele.getFirstElement() != null
							&& !(sele.getFirstElement() instanceof SystemBrowserDescriptor);
					remove.setEnabled(sel);
					edit.setEnabled(sel);
