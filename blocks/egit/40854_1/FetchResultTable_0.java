			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof RepositoryCommit) {
					return true;
				}
				return super.hasChildren(element);
			}
