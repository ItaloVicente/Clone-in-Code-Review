
		if (wantIds.isEmpty())
			return;

		AsyncRevObjectQueue q = walk.parseAny(wantIds, true);
		try {
			for (;;) {
				RevObject o;
				try {
					o = q.next();
				} catch (IOException error) {
					throw new PackProtocolException(MessageFormat.format(
							JGitText.get().notValid, error.getMessage()), error);
				}
				if (o == null)
					break;
				if (o.has(WANT)) {

				} else if (advertised.contains(o)) {
					o.add(WANT);
					wantAll.add(o);

					if (o instanceof RevTag) {
						o = walk.peel(o);
						if (o instanceof RevCommit) {
							if (!o.has(WANT)) {
								o.add(WANT);
								wantAll.add(o);
							}
						}
					}
				} else {
					throw new PackProtocolException(MessageFormat.format(
							JGitText.get().notValid, o.name()));
				}
			}
		} finally {
			q.release();
		}
