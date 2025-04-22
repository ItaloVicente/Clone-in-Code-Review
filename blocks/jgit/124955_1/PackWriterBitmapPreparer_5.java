
					int flags = nextFlg;
					nextIn = nextSpan(distanceFromTip);
					nextFlg = nextIn == distantCommitSpan
							? PackBitmapIndex.FLAG_REUSE
							: 0;

					BitmapBuilder fullBitmap = commitBitmapIndex
							.newBitmapBuilder();
					rw.reset();
					rw.markStart(c);
					rw.setRevFilter(new AddUnseenToBitmapFilter(
							selectionHelper.reusedCommitsBitmap

					while (rw.next() != null) {
					}

					List<BitmapCommit> longestAncestorChain = null;
					for (List<BitmapCommit> chain : chains) {
						BitmapCommit mostRecentCommit = chain
								.get(chain.size() - 1);
						if (fullBitmap.contains(mostRecentCommit)) {
							if (longestAncestorChain == null
									|| longestAncestorChain.size() < chain
											.size()) {
								longestAncestorChain = chain;
							}
						}
					}

					if (longestAncestorChain == null) {
						longestAncestorChain = new ArrayList<>();
						chains.add(longestAncestorChain);
					}
					longestAncestorChain.add(new BitmapCommit(c
							!longestAncestorChain.isEmpty()
					writeBitmaps.addBitmap(c
