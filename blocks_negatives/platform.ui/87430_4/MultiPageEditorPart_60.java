		editor.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propertyId) {
				MultiPageEditorPart.this.handlePropertyChange(propertyId);
			}
		});
