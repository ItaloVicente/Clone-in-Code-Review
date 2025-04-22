				SubMonitor progress = SubMonitor.convert(monitor, 2);
				FileDiff diffs[] = getDiffs(progress.newChild(1));
				setDocument(formatDiffs(diffs, progress.newChild(1)));
				return Status.OK_STATUS;
			}

			private FileDiff[] getDiffs(IProgressMonitor monitor) {
				List<FileDiff> diffResult = new ArrayList<>();

				if (base == null) {
					if (tip instanceof RepositoryCommit) {
						diffResult.addAll(
								asList(((RepositoryCommit) tip).getDiffs()));
						if (tip.getRevCommit().getParentCount() > 2) {
							RevCommit untrackedCommit = tip.getRevCommit()
									.getParent(
											StashEditorPage.PARENT_COMMIT_UNTRACKED);
							diffResult.addAll(asList(
									new RepositoryCommit(tip.getRepository(),
											untrackedCommit).getDiffs()));
						}
					}
				} else {
					Repository repository = tip.getRepository();
					RevCommit tipCommit = tip.getRevCommit();
					RevCommit baseCommit = base.getRevCommit();
					FileDiff[] diffsResult = null;
					try (RevWalk revWalk = new RevWalk(repository);
							TreeWalk treewalk = new TreeWalk(
									revWalk.getObjectReader())) {
						treewalk.setRecursive(true);
						treewalk.setFilter(TreeFilter.ANY_DIFF);
						revWalk.parseBody(tipCommit);
						revWalk.parseBody(baseCommit);
						diffsResult = FileDiff.compute(repository, treewalk,
								tipCommit, new RevCommit[] { baseCommit },
								monitor, TreeFilter.ALL);
					} catch (IOException e) {
						diffsResult = new FileDiff[0];
					}
					return diffsResult;
				}

				FileDiff[] result = diffResult.toArray(new FileDiff[0]);
				Arrays.sort(result, FileDiff.PATH_COMPARATOR);
				return result;
			}

			private DiffDocument formatDiffs(FileDiff[] diffs,
					IProgressMonitor monitor) {
				SubMonitor progress = SubMonitor.convert(monitor, diffs.length);
