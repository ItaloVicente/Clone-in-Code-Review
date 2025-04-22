			if (tag.getObject() instanceof RevCommit) {
				result.add(new CommitPropertyDescriptor(PROPERTY_TAG_TARGET,
						UIText.TagPropertySource_TagTarget,
						new RepositoryCommit(repository,
								(RevCommit) tag.getObject())));
			} else {
				result.add(new PropertyDescriptor(PROPERTY_TAG_TARGET,
						UIText.TagPropertySource_TagTarget));
			}
