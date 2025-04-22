			case REMOTES:
				return RepositoryViewUITexts.RepositoriesView_RemotesNodeText;
			case REMOTE:
				String name = (String) node.getObject();
				String url = node.getRepository().getConfig().getString(REMOTE,
						name, URL);
				if (url != null && !url.equals("")) //$NON-NLS-1$
					name = name + " - " + url; //$NON-NLS-1$
				return name;
