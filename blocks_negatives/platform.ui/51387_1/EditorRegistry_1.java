    private boolean contains(List editorsArray,
            IEditorDescriptor editorDescriptor) {
        IEditorDescriptor currentEditorDescriptor = null;
        Iterator i = editorsArray.iterator();
        while (i.hasNext()) {
            currentEditorDescriptor = (IEditorDescriptor) i.next();
            if (currentEditorDescriptor.getId()
                    .equals(editorDescriptor.getId())) {
