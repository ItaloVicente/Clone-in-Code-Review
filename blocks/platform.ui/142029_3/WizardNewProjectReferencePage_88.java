				IProject[] projects = ((IWorkspace) element).getRoot().getProjects();
				return projects == null ? new Object[0] : projects;
			}
		};
	}

	private static int getDefaultFontHeight(Control control, int lines) {
		FontData[] viewerFontData = control.getFont().getFontData();
		int fontHeight = 10;

		if (viewerFontData.length > 0) {
