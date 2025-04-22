
			String walkString = walker.getPathString();
			if (fullpaths.contains(walkString))
				return true;
			if (prefixes.contains(walkString))
				return true;
			for (int i = walkString.indexOf('/'); i > 0; i = walkString
					.indexOf('/'
				String prefix = walkString.substring(0
				if (fullpaths.contains(prefix))
