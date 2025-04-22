                getTreeViewer().getTree().showSelection();
            } else {
                getTreeViewer().setSelection(newSelection, true);
            }
        }
    }

    /**
     * Called when the context menu is about to open.
     * Delegates to the action group using the viewer's selection as the action context.
     * @since 2.0
     */
    protected void fillContextMenu(IMenuManager menu) {
