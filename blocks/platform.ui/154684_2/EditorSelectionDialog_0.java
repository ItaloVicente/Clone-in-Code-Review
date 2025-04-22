		editorTableViewer.setLabelProvider(createTextImageProvider(element-> {
			IEditorDescriptor d = (IEditorDescriptor) element;
			return TextProcessor.process(d.getLabel(), "."); //$NON-NLS-1$
		}, element -> {
			IEditorDescriptor d = (IEditorDescriptor) element;
			return (Image) resourceManager.get(d.getImageDescriptor());
		}));
