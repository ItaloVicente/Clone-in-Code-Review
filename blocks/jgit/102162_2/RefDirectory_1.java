				LockFile lck = lockPackedRefsOrThrow();
				try {
					PackedRefList cur = readPackedRefs();
					int idx = cur.find(name);
					if (0 <= idx)
						commitPackedRefs(lck
				} finally {
					lck.unlock();
				}
