        page.activate(part);
        page.toggleZoom(page.getReference(part));
        Assert.assertTrue(page.isPageZoomed());
        Assert.assertTrue(isZoomed(part));
    }

    protected void openEditor(IFile file, boolean activate) {
        try {
            if (file == file1) {
