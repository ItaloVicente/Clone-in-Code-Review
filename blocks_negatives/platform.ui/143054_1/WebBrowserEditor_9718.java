        Display display = getSite().getShell().getDisplay();
        close();
        display.asyncExec(runnable);
    }

    protected void doOpenExternalEditor(String id, IEditorInput input) {
        IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
        String name = input.getName();
        IEditorDescriptor [] editors = registry.getEditors(name);
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

        String editorId = null;
        for (IEditorDescriptor editor : editors) {
            if (editor.getId().equals(id))
                continue;
            editorId = editor.getId();
            break;
        }

        IEditorDescriptor ddesc = registry.getDefaultEditor(name);
        if (ddesc!=null && ddesc.getId().equals(id)) {
            int dot = name.lastIndexOf('.');
            String ext = name;
            if (dot!= -1)
            registry.setDefaultEditor(ext, null);
        }

         if (editorId==null) {
            if (registry.isSystemExternalEditorAvailable(name))
                editorId = IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID;
        }

        if (editorId!=null) {
            try {
                page.openEditor(input, editorId);
                return;
            } catch (PartInitException e) {
