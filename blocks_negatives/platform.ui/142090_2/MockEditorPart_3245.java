        this.input = input;
        setSite(site);
        callTrace.add("init");
        setSiteInitialized();
    }

    /**
     * @see IEditorPart#isDirty()
     */
    @Override
