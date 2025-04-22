		else {
			Ref branch;
			try {
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
							branch = null;
						else
							branch = node.getRepository().getRef(ref);
					}
				}
			} catch (IOException e) {
				branch = null;
			}
			if (branch != null)
				base = branch.getName();
		}
