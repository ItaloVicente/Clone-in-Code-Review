		editorTableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				IEditorDescriptor d = (IEditorDescriptor) element;
				return TextProcessor.process(d.getLabel(), "."); //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {
				IEditorDescriptor d = (IEditorDescriptor) element;
				return (Image) resourceManager.get(d.getImageDescriptor());
			}
		});
