    private void removeEditorFromMapping(HashMap map, IEditorDescriptor desc) {
        Iterator iter = map.values().iterator();
        FileEditorMapping mapping;
        IEditorDescriptor[] editors;
        while (iter.hasNext()) {
            mapping = (FileEditorMapping) iter.next();
            editors = mapping.getUnfilteredEditors();
