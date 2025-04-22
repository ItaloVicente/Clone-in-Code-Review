        String factoryID = memento
                .getString(IWorkbenchConstants.TAG_FACTORY_ID);
        IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryID);
        if (factory != null) {
            IAdaptable element = factory.createElement(memento);
            if (element instanceof IEditorInput) {
                editorInput = (IEditorInput) element;
                editorID = memento.getString(IWorkbenchConstants.TAG_ID);
            }
        }
        memento = null;
    }
