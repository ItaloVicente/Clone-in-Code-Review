		projectsList.setLabelProvider(new LabelProvider() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			public String getText(Object element) {
				return ((ProjectRecord) element).getProjectLabel();
