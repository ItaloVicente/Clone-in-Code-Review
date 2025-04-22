						if (checkReachable == null) {
							checkReachable = new ArrayList<RevCommit>();
							reachableFrom = advertised;
						}
						checkReachable.add((RevCommit) obj);
						break;
					case TIP:
						if (tips == null)
							tips = refIdSet(db.getAllRefs().values());
						if (!tips.contains(obj))
							throw new PackProtocolException(MessageFormat.format(
									JGitText.get().wantNotValid
						break;
					case REACHABLE_COMMIT_TIP:
						if (!(obj instanceof RevCommit)) {
							throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid
						}
						if (checkReachable == null) {
