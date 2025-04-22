						if (indexIter == null && headIter == null)
							continue;
						hasChanges = true;
						if (indexIter != null && wtIter.idEqual(indexIter))
							continue;
						if (headIter != null && wtIter.idEqual(headIter))
