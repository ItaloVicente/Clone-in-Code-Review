				IEditorDescriptor editor = mapping.getDefaultEditor();
				if (editor != null) {
					mappingImage = editor.getImageDescriptor();
					extensionImages.put(mappingKey, mappingImage);
					return mappingImage;
				}
			}
		}
