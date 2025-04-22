				if (wantIds.remove(obj)) {
					if (!advertised.contains(obj) && requestPolicy != RequestPolicy.ANY) {
						if (notAdvertisedWants == null)
							notAdvertisedWants = new HashSet<RevObject>();
						notAdvertisedWants.add(obj);
					}

					if (!obj.has(WANT)) {
						obj.add(WANT);
						wantAll.add(obj);
					}

					if (!(obj instanceof RevCommit))
						obj.add(SATISFIED);

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

