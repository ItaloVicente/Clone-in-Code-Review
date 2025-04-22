    public void setFocus() {
          filteredTreeViewer.getViewer().getControl().setFocus();
    }

    @Override
    public void setSelection(ISelection selection) {
        if (filteredTreeViewer != null || filteredTreeViewer.isDisposed()) {
            filteredTreeViewer.getViewer().setSelection(selection);
        }
