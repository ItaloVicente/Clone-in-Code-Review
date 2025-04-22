    /**
     * Returns the text of the currently selected readme segment.
     */
    private String getSegmentText() {
        StringBuilder result = new StringBuilder();
        ISelection selection = page.getSelection();
        if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection) {
            Object[] selected = ((IStructuredSelection) selection).toArray();
            for (int i = 0; i < selected.length; i++) {
                if (selected[i] instanceof MarkElement) {
                    result.append(((MarkElement) selected[i])
                            .getLabel(selected[i]));
                }
            }
        }
        return result.toString();
    }
