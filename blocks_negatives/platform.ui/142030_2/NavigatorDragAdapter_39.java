            for (IResource resource : resources) {
                try {
                    resource.refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                	 StatusManager.getManager().handle(e, IDEWorkbenchPlugin.IDE_WORKBENCH);
                }
            }
        }
    }

    /**
     * This implementation of {@link DragSourceListener#dragSetData(DragSourceEvent)}
     * sets the drag event data based on the current selection in the Navigator.
     */
    @Override
