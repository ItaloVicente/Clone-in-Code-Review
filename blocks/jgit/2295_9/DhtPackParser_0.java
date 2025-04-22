			if (linkIterators != null) {
				boolean removed = true;
				while (removed) {
					removed = false;
					for (ListIterator<DhtInfo> itr : linkIterators) {
						int cnt = 0;
						while (itr.hasPrevious() && cnt < linkBatchSize) {
							DhtInfo oe = itr.previous();
									ObjectIndexKey.create(repo
									chunkOf(oe.chunkPtr)
									dbWriteBuffer);
							cnt++;
						}
						if (0 < cnt)
							removed = true;
					}
				}
