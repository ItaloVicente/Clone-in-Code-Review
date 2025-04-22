				List<String> exclude = shallowExcludeRefs;
				if (exclude == null) {
					exclude = shallowExcludeRefs = new ArrayList<>();
				}
				exclude.add(line.substring(11));
				if (depth != 0) {
