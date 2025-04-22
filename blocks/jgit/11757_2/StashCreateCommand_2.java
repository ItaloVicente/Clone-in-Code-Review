					if (indexIter != null
							&& !indexIter.getDirCacheEntry().isMerged())
						throw new UnmergedPathsException(
								new UnmergedPathException(
										indexIter.getDirCacheEntry()));
					if (wtIter != null) {
						if (indexIter != null && wtIter.idEqual(indexIter)
								|| headIter != null
								&& wtIter.idEqual(headIter))
