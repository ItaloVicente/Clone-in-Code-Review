			Arrays.sort(childFiles, new Comparator<File>() {
				public int compare(File o1, File o2) {
					if (o1.isDirectory()) {
						if (o2.isDirectory()) {
							return o1.compareTo(o2);
						}
						return -1;
					} else if (o2.isDirectory()) {
						return 1;
					}
					return o1.compareTo(o2);
				}
			});
