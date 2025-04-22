        if (oldInput == null) {
            IResource resource = (IResource) newInput;
            resource.getWorkspace().addResourceChangeListener(this);
        }
        this.viewer = newViewer;
        this.input = (IResource) newInput;
    }

    /**
     * The workbench has changed.  Process the delta and provide updates to the viewer,
     * inside the UI thread.
     *
     * @see IResourceChangeListener#resourceChanged
     */
    @Override
