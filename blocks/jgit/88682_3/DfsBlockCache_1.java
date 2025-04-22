
					int slot = slot(dead.pack
					for (HashEntry entry = table
							.get(slot); entry != null; entry = entry.next) {
						if (entry.ref == dead) {
							ReentrantLock regionLock = lockFor(dead.pack
									dead.position);
							regionLock.lock();
							try {
								table.compareAndSet(slot
								break;
							} finally {
								regionLock.unlock();
							}
						}
					}

