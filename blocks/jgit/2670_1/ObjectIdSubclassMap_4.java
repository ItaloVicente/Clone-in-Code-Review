				if (found == size)
					throw new NoSuchElementException();

				for (;;) {
					if (blkIdx == Block.SIZE) {
						int useCnt = (1 << (dirBits - block.depth));
						if (dirTable.size() <= dirIdx + useCnt)
							throw new NoSuchElementException();
						dirIdx += useCnt;
						block = dirTable.get(dirIdx);
						blkIdx = 0;
					}

					V obj = block.members[blkIdx++];
					if (obj != null) {
