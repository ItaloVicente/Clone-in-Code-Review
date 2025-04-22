            if (viewer instanceof AbstractTreeViewer) {
                ((AbstractTreeViewer) viewer).add(resource, affected);
            } else {
                ((StructuredViewer) viewer).refresh(resource);
            }
        }
    }

    /**
     * The workbench has changed.  Process the delta and issue updates to the viewer,
     * inside the UI thread.
     *
     * @see IResourceChangeListener#resourceChanged
     */
    @Override
