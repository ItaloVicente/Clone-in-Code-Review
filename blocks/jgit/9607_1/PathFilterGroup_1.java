			max = paths[paths.length - 1];
			fullpaths = new HashSet<String>(paths.length);
			prefixes = new HashSet<String>(paths.length / 5);
			for (PathFilter pf : p) {
				fullpaths.add(pf.getPath());
				for (int i = pf.getPath().indexOf('/'); i > 0; i = pf.getPath()
						.indexOf('/'
					prefixes.add(pf.getPath().substring(0
					shouldBeRecursive = true;
				}
			}
