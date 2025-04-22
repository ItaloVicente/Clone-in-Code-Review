                }
                event.data = buffer.toString();
            }
            return;
        }
    }

    void restoreState(IMemento memento) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IMemento selectionMem = memento.getChild(TAG_SELECTION);
        if (selectionMem != null) {
            ArrayList selectionList = new ArrayList();
