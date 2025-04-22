
					LockFile rLck = heldLocks.get(refName);
					boolean shouldUnlock;
					if (rLck == null) {
						rLck = new LockFile(refFile);
						if (!rLck.lock()) {
							continue;
						}
						shouldUnlock = true;
					} else {
						shouldUnlock = false;
