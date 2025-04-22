                if (imageDesc == null) {
                    IEditorRegistry registry = WorkbenchPlugin.getDefault()
                            .getEditorRegistry();
                    imageDesc = registry.getImageDescriptor(input.getName());

                    if (imageDesc == null) {
                    }
                }
                if (imageDesc != null) {
                    image = (Image) disabledImageCache.get(imageDesc);
                    if (image == null) {
                        Image enabled = imageDesc.createImage();
                        Image disabled = new Image(editorsTable.getDisplay(),
                                enabled, SWT.IMAGE_DISABLE);
                        enabled.dispose();
                        disabledImageCache.put(imageDesc, disabled);
                        image = disabled;
                    }
                }
            }
            return image;
        }

        private void activate() {
            if (editorRef != null) {
                IEditorPart editor = editorRef.getEditor(true);
                WorkbenchPage p = (WorkbenchPage) editor.getEditorSite()
                        .getPage();
                Shell s = p.getWorkbenchWindow().getShell();
                if (s.getMinimized()) {
