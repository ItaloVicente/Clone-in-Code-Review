
					int slot = slot(dead.pack
					for (HashEntry entry = table
							.get(slot); entry != null; entry = entry.next) {
						if (entry.ref == dead) {
							table.compareAndSet(slot
							break;
						}
					}

