            IFile file = (IFile) obj;
            IWorkbenchPage page = getSite().getPage();
            IEditorPart editor = ResourceUtil.findEditor(page, file);
            if (editor != null) {
                page.bringToTop(editor);
                return;
            }
        }
    }

    /**
     * Creates the action group, which encapsulates all actions for the view.
     */
    protected void makeActions() {
    	MainActionGroup group = new MainActionGroup(this);
        setActionGroup(group);

        IHandlerService service = getSite().getService(IHandlerService.class);
