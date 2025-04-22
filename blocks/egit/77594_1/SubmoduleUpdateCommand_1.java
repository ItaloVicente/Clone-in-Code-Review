			try {
				boolean submodulesNodeSelected = false;
				List<Repository> subRepos = new ArrayList<>();
				for (RepositoryTreeNode<?> node : getSelectedNodes(event)) {
					if (node.getType() == RepositoryTreeNodeType.SUBMODULES) {
						submodulesNodeSelected = true;
						SubmoduleWalk walk = SubmoduleWalk
								.forIndex(node.getRepository());
						while (walk.next()) {
							Repository subRepo = walk.getRepository();
							subRepos.add(subRepo);
						}
						break;
					}
				}
				if (!submodulesNodeSelected) {
					for (Entry<Repository, List<String>> entry : repoPaths
							.entrySet()) {
						if (entry.getValue() != null) {
							for (String path : entry.getValue()) {
								Repository subRepo;
								subRepo = SubmoduleWalk.getSubmoduleRepository(
										entry.getKey(), path);
								subRepos.add(subRepo);
							}
						}
					}
				}
				Shell parent = getActiveShell(event);
				for (Repository subRepo : subRepos) {
					String repoName = Activator.getDefault().getRepositoryUtil()
							.getRepositoryName(subRepo);
					if (!UIRepositoryUtils.handleUncommittedFiles(subRepo,
							parent,
							MessageFormat.format(
									UIText.SubmoduleUpdateCommand_UncommittedChanges,
									repoName))) {
						return null;
					}
				}
			} catch (Exception e) {
				Activator.handleError(UIText.SubmoduleUpdateCommand_UpdateError,
						e, true);
				return null;
			}

