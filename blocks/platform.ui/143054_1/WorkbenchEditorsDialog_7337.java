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
