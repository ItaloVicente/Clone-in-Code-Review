		case REMOTE: {

			List<RepositoryTreeNode<String>> children = new ArrayList<RepositoryTreeNode<String>>();

			String remoteName = (String) node.getObject();
			RemoteConfig rc;
			try {
				rc = new RemoteConfig(node.getRepository().getConfig(),
						remoteName);
			} catch (URISyntaxException e) {
				return null;
			}

			if (!rc.getURIs().isEmpty())
				children.add(new RepositoryTreeNode<String>(node,
						RepositoryTreeNodeType.FETCH, node.getRepository(), rc
								.getURIs().get(0).toPrivateString()));

			if (!rc.getPushURIs().isEmpty())
				if (rc.getPushURIs().size() == 1)
					children.add(new RepositoryTreeNode<String>(node,
							RepositoryTreeNodeType.PUSH, node.getRepository(),
							rc.getPushURIs().get(0).toPrivateString()));
				else
					children.add(new RepositoryTreeNode<String>(node,
							RepositoryTreeNodeType.PUSH, node.getRepository(),
							rc.getPushURIs().get(0).toPrivateString() + "...")); //$NON-NLS-1$

			return children.toArray();

		}

		case FILE: // fall through
		case REF: // fall through
		case PUSH: // fall through
		case PROJ: // fall through
		case FETCH:
