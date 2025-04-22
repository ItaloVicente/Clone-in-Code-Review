			public StyledString getStyledText(Object element) {
				if (element instanceof FetchResultAdapter)
					return ((FetchResultAdapter) element)
							.getStyledText(element);
				if (element instanceof RepositoryCommit)
					return ((RepositoryCommit) element).getStyledText(element);
