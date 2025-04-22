	private final class CommitItemFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			boolean result = true;
			if (!showUntracked){
				if (element instanceof CommitItem) {
					CommitItem item = (CommitItem)element;
					if (item.status.equals(UIText.CommitDialog_StatusUntracked))
						result = false;
				}
			}
			return result;
		}
	}

