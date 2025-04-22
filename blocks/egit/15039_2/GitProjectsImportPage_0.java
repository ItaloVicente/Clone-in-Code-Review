	class ConflictingProjectFilter extends ViewerFilter {

		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			return !((ProjectRecord) element).hasConflicts();
		}

	}

