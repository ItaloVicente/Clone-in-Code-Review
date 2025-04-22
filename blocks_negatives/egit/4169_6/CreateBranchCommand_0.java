				if (node.getRepository().getFullBranch().startsWith(
						Constants.R_HEADS)) {
					branch = node.getRepository().getRef(
							node.getRepository().getFullBranch());
				} else {
					String ref = Activator
							.getDefault()
							.getRepositoryUtil()
							.mapCommitToRef(node.getRepository(),
									node.getRepository().getFullBranch(), false);
					if (ref == null)
						branch = null;
					else {
						if (ref.startsWith(Constants.R_TAGS))
