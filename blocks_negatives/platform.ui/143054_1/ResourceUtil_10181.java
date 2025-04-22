            IEditorPart part = ref.getEditor(false);
            if (part != null) {
                IFile editorFile = getFile(part.getEditorInput());
                if (editorFile != null && file.equals(editorFile)) {
                    return part;
                }
            }
        }
        return null;
    }

    /**
     * Returns the resource corresponding to the given model element, or <code>null</code>
     * if there is no applicable resource.
     *
     * @param element the model element, or <code>null</code>
     * @return the resource corresponding to the model element, or <code>null</code>
     * @since 3.2
     */
    public static IResource getResource(Object element) {
