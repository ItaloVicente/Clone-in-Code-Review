			case TAGS:
				StyledString tagsLabel = new StyledString(getSimpleText(node));
				addCount(tagsLabel, node.getRepository().getTags().size());
				return tagsLabel;
			case REMOTES:
				StyledString remotesLabel = new StyledString(
						getSimpleText(node));
				addCount(remotesLabel, node.getRepository().getConfig()
						.getSubsections(RepositoriesView.REMOTE).size());
				return remotesLabel;
