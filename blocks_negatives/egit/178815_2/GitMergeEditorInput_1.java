				String target;
				switch (repo.getRepositoryState()) {
				case MERGING:
					target = Constants.MERGE_HEAD;
					break;
				case CHERRY_PICKING:
					target = Constants.CHERRY_PICK_HEAD;
					break;
				case REBASING_INTERACTIVE:
					target = readFile(repo.getDirectory(),
							RebaseCommand.REBASE_MERGE + File.separatorChar
									+ RebaseCommand.STOPPED_SHA);
					break;
				case REVERTING:
					target = Constants.REVERT_HEAD;
					break;
				default:
					target = Constants.ORIG_HEAD;
					break;
				}
				ObjectId mergeHead = repo.resolve(target);
				if (mergeHead == null)
					throw new IOException(NLS.bind(
							CoreText.ValidationUtils_CanNotResolveRefMessage,
							target));
				rightCommit = rw.parseCommit(mergeHead);
