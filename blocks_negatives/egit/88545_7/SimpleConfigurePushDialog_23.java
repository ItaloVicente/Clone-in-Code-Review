			@Override
			public void widgetSelected(SelectionEvent e) {
				URIish uri = (URIish) ((IStructuredSelection) uriViewer
						.getSelection()).getFirstElement();
				config.removePushURI(uri);
				updateControls();
			}
		});

		uriViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				deleteUri.setEnabled(!uriViewer.getSelection().isEmpty());
				changeUri.setEnabled(((IStructuredSelection) uriViewer
						.getSelection()).size() == 1);
			}
		});

		final Group refSpecGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, true).minSize(SWT.DEFAULT, SWT.DEFAULT).applyTo(refSpecGroup);
		refSpecGroup.setText(UIText.SimpleConfigurePushDialog_RefMappingGroup);
		refSpecGroup.setLayout(new GridLayout(2, false));
