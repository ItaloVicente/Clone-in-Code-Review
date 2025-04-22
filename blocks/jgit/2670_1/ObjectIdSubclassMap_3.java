			if (old != null)
				return old;


			final int bDepth = b.depth;
			if (bDepth == dirBits) {
				final int cnt = dirTable.size();
				dirTable.setSize(cnt << 1);
				for (int i = cnt - 1; 0 <= i; i--) {
					Block<V> o = dirTable.get(i);
					dirTable.set(i << 1
					dirTable.set((i << 1) | 1
				}
				dirShift = 32 - ++dirBits;
			}

			Block<V> b0 = newBlock(bDepth + 1);
			Block<V> b1 = newBlock(bDepth + 1);

			final int nMask = 1 << (32 - b0.depth);
			for (int i = 0; i < Block.SIZE; i++) {
				V obj = b.members[i];
				if (obj == null)
					continue;
				if ((obj.w1 & nMask) == 0)
					b0.appendExisting(obj);
				else
					b1.appendExisting(obj);
			}

			int useCnt = (1 << (dirBits - bDepth)) >> 1;
			int i = hash(newValue);
			while (0 < i && dirTable.get(i - 1) == b)
				i--;
			for (int n = 0; n < useCnt; n++) {
				if (dirTable.set(i++
					throw new IllegalStateException();
			}
			for (int n = 0; n < useCnt; n++) {
				if (dirTable.set(i++
					throw new IllegalStateException();
			}

			freeList.add(b);
