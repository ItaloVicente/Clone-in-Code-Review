			Collections.sort(children, new Comparator<PathNode>() {
				public int compare(PathNode o1, PathNode o2) {
					int diff = o1.type.ordinal() - o2.type.ordinal();
					if (diff != 0)
						return diff;
					return o1.path.toString().compareTo(o2.path.toString());
				}
			});
