		item = new MenuItem(menu, SWT.PUSH);
		item.setText(UIText.CommitDialog_PasteFileNameToCommitMSG);
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				IStructuredSelection sel = (IStructuredSelection) filesViewer.getSelection();
				if (sel.isEmpty() || sel.size() != 1) {
					return;
				}
				CommitItem commitItem = (CommitItem) sel.getFirstElement();
				commitText.getTextWidget().insert(commitItem.file.getName());
			}
		});
