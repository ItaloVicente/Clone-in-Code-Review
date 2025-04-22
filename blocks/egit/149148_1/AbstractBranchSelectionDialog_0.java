	private static final class RefNamePatternFilter extends PatternFilter {
		@Override
		protected boolean isLeafMatch(Viewer viewer, Object element) {
			if (super.isLeafMatch(viewer, element)) {
				return true;
			}
			if (element instanceof RefNode) {
				String branchName = ((RefNode) element).getObject()
						.getName();
				return wordMatches(branchName);
			}
			return false;
		}
	}

