				if (s == null) {
					try {
						s = hash(OLD
					} catch (TableFullException tableFull) {
						tableOverflow = true;
						continue SRC;
					}
				}

				SimilarityIndex d;
				try {
					d = hash(NEW
				} catch (TableFullException tableFull) {
					if (dstTooLarge == null)
						dstTooLarge = new BitSet(dsts.size());
					dstTooLarge.set(dstIdx);
					tableOverflow = true;
					pm.update(1);
					continue;
				}

