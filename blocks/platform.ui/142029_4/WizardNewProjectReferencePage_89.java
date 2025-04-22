		return lines * fontHeight;

	}

	public IProject[] getReferencedProjects() {
		Object[] elements = referenceProjectsViewer.getCheckedElements();
		IProject[] projects = new IProject[elements.length];
		System.arraycopy(elements, 0, projects, 0, elements.length);
		return projects;
	}
