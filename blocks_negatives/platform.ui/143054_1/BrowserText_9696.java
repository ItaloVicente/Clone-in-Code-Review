        exception = new Text(parent, SWT.MULTI);
        loadExceptionText();
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.exclude = true;
        exception.setLayoutData(gd);
    }

    private void loadExceptionText() {
        StringWriter swriter = new StringWriter();
