		Map<PackExt
				new Comparator<PackExt>() {
					public int compare(PackExt o1
						if (o1 == o2)
							return 0;
						if (o1 == PackExt.INDEX)
							return 1;
						if (o2 == PackExt.INDEX)
							return -1;
						return Integer.signum(o1.hashCode() - o2.hashCode());
					}

				});
