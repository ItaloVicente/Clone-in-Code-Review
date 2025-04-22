		editorAssociationsViewer.setLabelProvider(
				createTextImageProvider(element -> ((IEditorDescriptor) element).getLabel(),
						element -> {
							Image res = ((IEditorDescriptor) element).getImageDescriptor().createImage();
							if (res != null) {
								disposableEditorIcons.add(res);
							}
							return res;
						}));
