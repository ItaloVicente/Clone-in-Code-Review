
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
