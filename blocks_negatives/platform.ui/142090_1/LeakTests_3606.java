        try {
            contextMenuEditor = null;
            fActivePage.closeEditor(editor, false);
            editor = null;
            checkRef(queue, ref);
        } finally {
            ref.clear();
        }
    }
