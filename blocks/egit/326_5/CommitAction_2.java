				try {
					if (Team.isIgnoredHint(resource) || RepositoryMapping.isIgnored(resource))
						return false;
				} catch (IOException e) {
					throw new TeamException(UIText.CommitAction_InternalError, e);
				}
