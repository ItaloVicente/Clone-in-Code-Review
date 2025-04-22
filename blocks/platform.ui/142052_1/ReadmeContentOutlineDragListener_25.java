	private String getSegmentText() {
		StringBuilder result = new StringBuilder();
		ISelection selection = page.getSelection();
		if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection) {
			Object[] selected = ((IStructuredSelection) selection).toArray();
			result.append("\n"); //$NON-NLS-1$
			for (int i = 0; i < selected.length; i++) {
				if (selected[i] instanceof MarkElement) {
					result.append(((MarkElement) selected[i]).getLabel(selected[i]));
					result.append("\n"); //$NON-NLS-1$
				}
			}
		}
		return result.toString();
	}
