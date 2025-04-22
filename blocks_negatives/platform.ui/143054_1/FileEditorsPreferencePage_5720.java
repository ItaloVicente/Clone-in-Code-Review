                if (defaultString != null) {
                } else {
                    item.setText(editor.getLabel());
                }
                item.setImage(getImage(editor));
            }

			EditorRegistry registry = (EditorRegistry) WorkbenchPlugin
					.getDefault().getEditorRegistry();
			IContentType[] contentTypes = Platform.getContentTypeManager()
					.findContentTypesFor(resourceType.getLabel());
