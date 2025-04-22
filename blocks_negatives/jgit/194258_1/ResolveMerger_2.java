					case OURS:
						keep(ourDce);
						return true;
					case THEIRS:
						DirCacheEntry theirEntry = add(tw.getRawPath(), theirs,
								DirCacheEntry.STAGE_0, EPOCH, 0);
						addToCheckout(tw.getPathString(), theirEntry, attributes);
						return true;
					default:
						result = new MergeResult<>(Collections.emptyList());
						result.setContainsConflicts(true);
						break;
