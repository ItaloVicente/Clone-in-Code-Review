			private PackSourceComparator(Map<PackSource
				if (!ranks.keySet().equals(
							new HashSet<>(Arrays.asList(PackSource.values())))) {
					throw new IllegalArgumentException();
				}
				this.ranks = new HashMap<>(ranks);
			}

			@Override
			public int compare(PackSource a
				return ranks.get(a).compareTo(ranks.get(b));
			}

			@Override
			public String toString() {
				return Arrays.stream(PackSource.values())
						.collect(joining("
			}
