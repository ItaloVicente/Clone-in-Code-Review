					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							final Set<Object> toAdd = new HashSet<>();
							final Set<Object> toRemove = new HashSet<>();

							int delta = (randomNumberGenerator
									.nextInt(AVERAGE_DELTA * 4) - AVERAGE_DELTA * 2);
							int extraAdds = randomNumberGenerator
									.nextInt(AVERAGE_DELTA);
							int addCount = delta + extraAdds;
							int removeCount = -delta + extraAdds;

							if (addCount > 0) {
								for (int i = 0; i < addCount; i++) {
									toAdd.add(Integer.valueOf(randomNumberGenerator
											.nextInt(20)));
								}
							}

							if (removeCount > 0) {
								Iterator<Object> oldElements = wrappedSet.iterator();
								for (int i = 0; i < removeCount
										&& oldElements.hasNext(); i++) {
									toRemove.add(oldElements.next());
								}
							}

							toAdd.removeAll(wrappedSet);
							wrappedSet.addAll(toAdd);
							wrappedSet.removeAll(toRemove);

							setStale(false);
							fireSetChange(Diffs.createSetDiff(toAdd, toRemove));
