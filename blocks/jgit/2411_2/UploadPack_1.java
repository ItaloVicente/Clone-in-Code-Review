				if (wantIds.remove(obj)) {
					if (!advertised.contains(obj)) {
						throw new PackProtocolException(MessageFormat.format(
								JGitText.get().notValid
					}

					if (!obj.has(WANT)) {
						obj.add(WANT);
						wantAll.add(obj);
					}

					if (obj instanceof RevTag) {
						RevObject target = walk.peel(obj);
						if (target instanceof RevCommit) {
							if (!target.has(WANT)) {
								target.add(WANT);
								wantAll.add(target);
							}
						}
					}

					if (!peerHasSet.contains(obj))
						continue;
				}

