					switch (requestPolicy) {
					case ADVERTISED:
					default:
						throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid, obj));
					case REACHABLE_COMMIT:
						if (!(obj instanceof RevCommit)) {
							throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid, obj));
						}
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
									JGitText.get().wantNotValid, obj));
						break;
					case REACHABLE_COMMIT_TIP:
						if (!(obj instanceof RevCommit)) {
							throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid, obj));
						}
						if (checkReachable == null) {
							checkReachable = new ArrayList<RevCommit>();
							reachableFrom = refIdSet(db.getAllRefs().values());
						}
						checkReachable.add((RevCommit) obj);
						break;
					case ANY:
						break;
					}
