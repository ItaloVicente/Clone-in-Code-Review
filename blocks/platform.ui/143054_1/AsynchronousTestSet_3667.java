					}

					toAdd.removeAll(wrappedSet);
					wrappedSet.addAll(toAdd);
					wrappedSet.removeAll(toRemove);

					setStale(false);
					fireSetChange(Diffs.createSetDiff(toAdd, toRemove));
				});
