				List<BitmapCommit> longestAncestorChain = null;
				for (List<BitmapCommit> chain : chains) {
					BitmapCommit mostRecentCommit = chain.get(chain.size() - 1);
					if (fullBitmap.contains(mostRecentCommit)) {
						if (longestAncestorChain == null
								|| longestAncestorChain.size() < chain.size()) {
							longestAncestorChain = chain;
