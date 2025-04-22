		} else if (IPropertySheetPage.class == adapter) {
			PropertySheetPage page = new GitPropertySheetPage();
			page.setPropertySourceProvider(object -> {
				if (object instanceof IPropertySource) {
					return (IPropertySource) object;
				}
				if (object instanceof IRepositoryCommit) {
					return new CommitPropertySource(
							((IRepositoryCommit) object).getRevCommit(), page);
				}
				return null;
			});
			return adapter.cast(page);
