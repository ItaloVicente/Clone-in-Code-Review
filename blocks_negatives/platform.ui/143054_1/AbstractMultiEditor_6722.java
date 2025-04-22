    }

    /*
     * @see IEditorPart#init(IEditorSite, IEditorInput)
     */
    @Override
	public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        init(site, (MultiEditorInput) input);
    }

    /**
     * @param site
     * @param input
     * @throws PartInitException
