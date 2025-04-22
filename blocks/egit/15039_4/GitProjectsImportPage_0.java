	class ConflictingProjectFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			return !((ProjectRecord) element).hasConflicts();
		}

	}

