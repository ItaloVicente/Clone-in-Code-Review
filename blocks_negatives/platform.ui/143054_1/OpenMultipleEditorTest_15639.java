        for (int i = 0; i < 100; i++) {
            IFile file = getProject().getFile(i + "." + extension);
            IDE.openEditor(activePage, file, true);
            processEvents();
        }
        if (closeAll) {
            activePage.closeAllEditors(false);
        }
        else {
            IEditorPart [] parts = activePage.getEditors();
            for (int i = 0; i < parts.length; i++) {
                activePage.closeEditor(parts[i], false);
            }
        }
        stopMeasuring();
        commitMeasurements();
        assertPerformance();
    }
