				String sourceRef = node.getRepository().getConfig().getString(
						ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
						ConfigConstants.CONFIG_KEY_DEFBRANCHSTARTPOINT);
				if (node.getRepository().getRef(sourceRef) != null)
					branch = node.getRepository().getRef(sourceRef);
				else {
					if (node.getRepository().getFullBranch()
							.startsWith(Constants.R_HEADS)) {
						branch = node.getRepository().getRef(
								node.getRepository().getFullBranch());
					} else {
						String ref = Activator
								.getDefault()
								.getRepositoryUtil()
								.mapCommitToRef(node.getRepository(),
										node.getRepository().getFullBranch(),
										false);
						if (ref == null)
