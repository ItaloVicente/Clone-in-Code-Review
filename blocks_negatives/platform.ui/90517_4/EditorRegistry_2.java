        FileEditorMapping maps[] = typeEditorMappings.userMappings();
        for (int mapsIndex = 0; mapsIndex < maps.length; mapsIndex++) {
            FileEditorMapping type = maps[mapsIndex];
			IMemento editorMemento = memento.createChild(IWorkbenchConstants.TAG_INFO);
			editorMemento.putString(IWorkbenchConstants.TAG_NAME, type.getName());
			editorMemento.putString(IWorkbenchConstants.TAG_EXTENSION, type.getExtension());
            IEditorDescriptor[] editorArray = type.getEditors();
