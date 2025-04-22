                if (currentEditor != null) {
                    Object selectedResource = currentEditor.getEditorInput()
                            .getAdapter(IResource.class);
                    if (selectedResource != null) {
                        selection = new StructuredSelection(selectedResource);
                    }
                }
