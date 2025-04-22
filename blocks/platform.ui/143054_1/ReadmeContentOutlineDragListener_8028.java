	private String getSegmentText() {
		StringBuilder result = new StringBuilder();
		ISelection selection = page.getSelection();
		if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection) {
			Object[] selected = ((IStructuredSelection) selection).toArray();
			result.append("\n"); //$NON-NLS-1$
			for (Object a : selected) {
				if (a instanceof MarkElement) {
					result.append(((MarkElement) a).getLabel(a));
					result.append("\n"); //$NON-NLS-1$
				}
			}
		}
		return result.toString();
	}
