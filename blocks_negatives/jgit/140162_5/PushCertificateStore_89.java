		return new Iterable<PushCertificate>() {
			@Override
			public Iterator<PushCertificate> iterator() {
				return new Iterator<PushCertificate>() {
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
										PathFilterGroup.create(
											Collections.singleton(PathFilter.create(path))),
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

					@Override
					public boolean hasNext() {
						try {
							if (next == null) {
								if (rw == null) {
									return false;
								}
								try {
									RevCommit c = rw.next();
									if (c != null) {
										try (TreeWalk tw = TreeWalk.forPath(
												rw.getObjectReader(), path, c.getTree())) {
											next = read(tw);
										}
									} else {
										next = null;
									}
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							}
							return next != null;
						} finally {
							if (next == null && rw != null) {
								rw.close();
								rw = null;
							}
						}
					}

					@Override
					public PushCertificate next() {
						hasNext();
						PushCertificate n = next;
						if (n == null) {
							throw new NoSuchElementException();
						}
						next = null;
						return n;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
