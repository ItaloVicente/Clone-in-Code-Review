		setDetailsLabelProvider(new GitLabelProvider() {
			@Override
			public Image getImage(Object element) {
				if(element instanceof RepositoryCommit) {
					RepositoryCommit commit = (RepositoryCommit) element;
					return super.getImage(commit.getRepository());
				}
				return super.getImage(element);
			}
			@Override
			public String getText(Object element) {
				if(element instanceof RepositoryCommit) {
					RepositoryCommit commit = (RepositoryCommit) element;
					return super.getText(commit.getRepository());
				}
				return super.getText(element);
			}
		});
