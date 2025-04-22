        IFile inputFile = ResourceUtil.getFile(input);
        if (inputFile != null && inputFile.getParent() instanceof IProject) {
            String name = inputFile.getName();
            if (name.equals("plugin.xml") || name.equals("MANIFEST.MF") || name.equals("build.properties")) {
                try {
                    IFile editorFile = ResourceUtil.getFile(editorRef.getEditorInput());
                    return editorFile != null && inputFile.getProject().equals(editorFile.getProject());
                } catch (PartInitException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
