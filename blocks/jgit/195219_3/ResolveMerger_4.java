				case OURS:
					keep(ourDce);
					return true;
				case THEIRS:
					DirCacheEntry e = add(tw.getRawPath()
							DirCacheEntry.STAGE_0
					if (e != null) {
						addToCheckout(tw.getPathString()
					}
					return true;
				default:
					result = new MergeResult<>(Collections.emptyList());
					result.setContainsConflicts(true);
					break;
