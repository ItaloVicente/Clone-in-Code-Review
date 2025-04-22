				} else {
					longestAncestorChain = candidateChain.get(0);
					for (List<BitmapCommit> chain : candidateChain) {
						if (chain.size() > longestAncestorChain.size()) {
							longestAncestorChain = chain;
						}
					}
