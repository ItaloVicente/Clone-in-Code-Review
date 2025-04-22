		case REPOGROUP: {
			List<String> repoDirs = ((RepositoryGroupNode) node).getGroup()
					.getRepositoryDirectories();
			return getRepositoryNodes(
					Activator.getDefault().getRepositoryUtil(), null, node,
					repoDirs).toArray();
		}

