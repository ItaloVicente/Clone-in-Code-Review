        exception.setText(swriter.toString());
    }

    protected void toggleException() {
        expanded = !expanded;
        updateButtonText();
        GridData gd = (GridData) exception.getLayoutData();
        gd.exclude = !expanded;
        exception.setVisible(expanded);
        refresh();
    }

    private void updateButtonText() {
        if (expanded)
            button.setText(Messages.BrowserText_button_collapse);
        else
            button.setText(Messages.BrowserText_button_expand);
    }

    protected void updateWidth(Composite parent) {
        Rectangle area = parent.getClientArea();
        updateWidth(title, area.width);
        updateWidth(text, area.width);
        updateWidth(sep, area.width);
        updateWidth(link, area.width);
        updateWidth(exTitle, area.width);
        updateWidth(exception, area.width);
    }

    private void updateWidth(Control c, int width) {
        GridData gd = (GridData) c.getLayoutData();
        if (gd != null)
            gd.widthHint = width - 10;
    }

    protected void doOpenExternal() {
        IBrowserViewerContainer container = viewer.getContainer();
        if (container != null)
            container.openInExternalBrowser(url);
    }

    public Control getControl() {
        return scomp;
    }

    public boolean setUrl(String url) {
        this.url = url;
        return true;
    }

    public void setFocus() {
        link.setFocus();
    }

    public String getUrl() {
        return url;
    }

    public void refresh() {
        scomp.reflow(true);
    }
