					if (headIter != null && indexIter != null && wtIter != null) {
						if (!indexIter.getDirCacheEntry().isMerged())
							throw new UnmergedPathsException(
									new UnmergedPathException(
											indexIter.getDirCacheEntry()));
						if (wtIter.idEqual(indexIter)
								|| wtIter.idEqual(headIter))
