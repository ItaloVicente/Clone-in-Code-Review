
					BitmapCommit bc = BitmapCommit.newBuilder(c).setFlags(flags)
							.setAddToIndex(currDist >= distanceThreshold)
							.setReuseWalker(!longestAncestorChain.isEmpty())
							.build();
					longestAncestorChain.add(bc);
