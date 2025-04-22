	private final class ProjectLabelProvider extends LabelProvider implements
			IColorProvider {
		public String getText(Object element) {
			return ((ProjectRecord) element).getProjectLabel();
		}

		public Color getForeground(Object element) {
			if (isProjectInWorkspace(((ProjectRecord) element).getProjectName()))
				return PlatformUI.getWorkbench().getDisplay().getSystemColor(
						SWT.COLOR_GRAY);
			return null;
		}

		public Color getBackground(Object element) {
			return null;
		}
	}

