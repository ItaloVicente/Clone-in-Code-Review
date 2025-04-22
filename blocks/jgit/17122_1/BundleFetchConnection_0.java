			try {
				Map<String
					transport.local.getRefDatabase().getRefs(ALL);
				for (final Ref r : localRefs.values()) {
					try {
						rw.markStart(rw.parseCommit(r.getObjectId()));
					} catch (IOException readError) {
					}
