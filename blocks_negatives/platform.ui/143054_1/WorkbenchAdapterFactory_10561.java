        return new Class[] { IWorkbenchAdapter.class, IWorkbenchAdapter2.class,
                IWorkbenchAdapter3.class, IElementFactory.class,
                IPersistableElement.class, IActionFilter.class,
                IUndoContext.class };
    }

    /**
     * Returns an object which is an instance of IElementFactory
     * associated with the given object. Returns <code>null</code> if
     * no such object can be found.
     */
