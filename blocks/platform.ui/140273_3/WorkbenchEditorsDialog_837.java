			if (editorRef != null) {
				image = editorRef.getTitleImage();
			} else {
				ImageDescriptor imageDesc = null;
				if (desc != null) {
					imageDesc = desc.getImageDescriptor();
				}
				if (imageDesc == null) {
					IEditorRegistry registry = WorkbenchPlugin.getDefault().getEditorRegistry();
					imageDesc = registry.getImageDescriptor(input.getName());

					if (imageDesc == null) {
					}
				}
				if (imageDesc != null) {
					image = (Image) disabledImageCache.get(imageDesc);
					if (image == null) {
						Image enabled = imageDesc.createImage();
						Image disabled = new Image(editorsTable.getDisplay(), enabled, SWT.IMAGE_DISABLE);
						enabled.dispose();
						disabledImageCache.put(imageDesc, disabled);
						image = disabled;
					}
