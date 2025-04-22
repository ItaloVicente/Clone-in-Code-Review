    void saveState(IMemento mem) {
        if (editorInput != null) {
            IPersistableElement persistable = editorInput.getPersistable();
            mem.putString(IWorkbenchConstants.TAG_ID, editorID);
            mem.putString(IWorkbenchConstants.TAG_FACTORY_ID, persistable
                    .getFactoryId());
            persistable.saveState(mem);
        } else if (memento != null) {
            mem.putMemento(memento);
        }
    }
