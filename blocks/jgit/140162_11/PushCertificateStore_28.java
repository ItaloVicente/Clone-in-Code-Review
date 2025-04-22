		return () -> new Iterator<PushCertificate>() {
			private final String path = pathName(refName);

			private PushCertificate next;

			private RevWalk rw;
			{
				try {
					if (reader == null) {
						load();
					}
					if (commit != null) {
						rw = new RevWalk(reader);
						rw.setTreeFilter(AndTreeFilter.create(
								PathFilterGroup.create(Collections
										.singleton(PathFilter.create(path)))
								TreeFilter.ANY_DIFF));
						rw.setRewriteParents(false);
						rw.markStart(rw.parseCommit(commit));
					} else {
						rw = null;
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
