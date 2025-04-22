			fullpaths = new HashSet<String>(paths.length);
			prefixes = new HashSet<String>(paths.length / 5);
			for (PathFilter pf : p) {
				fullpaths.add(pf.getPath());
				if (max == null || max.getPath().compareTo(pf.getPath()) < 0)
					max = pf;
				for (int i = pf.getPath().indexOf('/'); i > 0; i = pf.getPath()
						.indexOf('/'
					prefixes.add(pf.getPath().substring(0
					shouldBeRecursive = true;
				}
			}
