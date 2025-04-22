		fTreeViewer.addDoubleClickListener(event -> {
			Object element = ((IStructuredSelection) event.getSelection())
					.getFirstElement();
			fTreeViewer.setChecked(element, !fTreeViewer.getChecked(element));
			enableOk();
		});
