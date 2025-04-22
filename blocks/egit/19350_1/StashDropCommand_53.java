				monitor.beginTask(UIText.StashDropCommand_jobTitle,
						nodes.size());

				Collections.sort(nodes, new Comparator<StashedCommitNode>() {

					public int compare(StashedCommitNode n1,
							StashedCommitNode n2) {
						return n1.getIndex() < n2.getIndex() ? 1 : -1;
					}
				});

				for (StashedCommitNode node : nodes) {
					final int index = node.getIndex();
					if (index < 0)
						return null;
					final RevCommit commit = node.getObject();
					if (commit == null)
						return null;
					final String stashName = node.getObject().getName();
					final StashDropOperation op = new StashDropOperation(repo,
							node.getIndex());
					monitor.subTask(stashName);
					try {
						op.execute(monitor);
					} catch (CoreException e) {
						Activator.logError(MessageFormat.format(
								UIText.StashDropCommand_dropFailed,
								node.getObject().name()), e);
					}
					monitor.worked(1);
