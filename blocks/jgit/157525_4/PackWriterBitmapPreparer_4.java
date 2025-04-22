
					BitmapCommit bc = BitmapCommit.newBuilder(c).setFlags(flags)
							.setAddToIndex(currDist >= DISTANCE_THRESHOLD)
							.setReuseWalker(!longestAncestorChain.isEmpty())
							.build();
					longestAncestorChain.add(bc);
