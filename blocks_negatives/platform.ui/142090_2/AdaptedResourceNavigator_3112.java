        Object obj = selection.getFirstElement();
        if (obj instanceof IFile && selection.size() == 1) {
            IFile file = (IFile) obj;
            IWorkbenchPage page = getSite().getPage();
            IEditorReference editorArray[] = page.getEditorReferences();
            for (IEditorReference element : editorArray) {
                IEditorPart editor = element.getEditor(true);
                IEditorInput input = editor.getEditorInput();
                if (input instanceof IFileEditorInput
                        && file.equals(((IFileEditorInput) input).getFile())) {
                    page.bringToTop(editor);
                    return;
                }
            }
        }
    }

    /**
     *	Create self's action objects
     */
    protected void makeActions() {
        actionGroup = new TestNavigatorActionGroup(this);
    }

    /**
     * Restore the state of the receiver to the state described in
     * momento.
     * @since 2.0
     */

    protected void restoreState(IMemento memento) {
    }

    @Override
