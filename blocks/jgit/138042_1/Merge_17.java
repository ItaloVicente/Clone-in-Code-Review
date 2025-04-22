				outw.println(result.getMergeStatus().toString());
				break;
			case CHECKOUT_CONFLICT:
				outw.println(CLIText.get().mergeCheckoutConflict);
				for (String collidingPath : result.getCheckoutConflicts()) {
				}
				outw.println(CLIText.get().mergeCheckoutFailed);
				break;
			case CONFLICTING:
				for (String collidingPath : result.getConflicts().keySet())
					outw.println(MessageFormat.format(
							CLIText.get().mergeConflict
				outw.println(CLIText.get().mergeFailed);
				break;
			case FAILED:
				for (Map.Entry<String
						.getFailingPaths().entrySet())
					switch (entry.getValue()) {
					case DIRTY_WORKTREE:
					case DIRTY_INDEX:
						outw.println(CLIText.get().dontOverwriteLocalChanges);
						break;
					case COULD_NOT_DELETE:
						outw.println(CLIText.get().cannotDeleteFile);
						break;
					}
				break;
			case MERGED:
				MergeStrategy strategy = isMergedInto(oldHead
						? MergeStrategy.RECURSIVE
						: mergeStrategy;
				outw.println(MessageFormat.format(CLIText.get().mergeMadeBy
						strategy.getName()));
				break;
			case MERGED_NOT_COMMITTED:
				outw.println(
						CLIText.get().mergeWentWellStoppedBeforeCommitting);
				break;
			case MERGED_SQUASHED:
			case FAST_FORWARD_SQUASHED:
			case MERGED_SQUASHED_NOT_COMMITTED:
				outw.println(CLIText.get().mergedSquashed);
				outw.println(
						CLIText.get().mergeWentWellStoppedBeforeCommitting);
				break;
			case ABORTED:
				throw die(CLIText.get().ffNotPossibleAborting);
			case NOT_SUPPORTED:
				outw.println(MessageFormat.format(
						CLIText.get().unsupportedOperation
			}
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
