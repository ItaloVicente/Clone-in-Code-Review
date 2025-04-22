		Map<PackExt, File> tmpExts = new TreeMap<>(
				new Comparator<PackExt>() {
					@Override
					public int compare(PackExt o1, PackExt o2) {
						if (o1 == o2)
							return 0;
						if (o1 == PackExt.INDEX)
							return 1;
						if (o2 == PackExt.INDEX)
							return -1;
						return Integer.signum(o1.hashCode() - o2.hashCode());
					}

				});
