			final byte[] rawPath = walker.getRawPath();
			Comparator comparator = new Comparator<Object>() {
				public int compare(Object pf
					PathFilter pathFilter = (PathFilter) pf;
					return -walker.isPathPrefix(pathFilter.pathRaw
							pathFilter.pathRaw.length);
				}
			};

			Object[] pathsObject = paths;
			Object rawObject = rawPath;
			@SuppressWarnings("unchecked")
			int position = Arrays.binarySearch(pathsObject
					comparator);
			if (position >= 0)
				return true;
			if (position == -paths.length - 1)
				throw StopWalkException.INSTANCE;
			return false;
