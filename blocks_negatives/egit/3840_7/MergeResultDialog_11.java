			public String getColumnText(Object element, int columnIndex) {
				ObjectId commitId = (ObjectId) element;
				if (columnIndex == 0)
					return abbreviate(commitId, false);
				else if (columnIndex == 1)
					return getCommitMessage(commitId);
				return EMPTY;
