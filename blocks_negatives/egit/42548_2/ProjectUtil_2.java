			try {
				String canonicalPath = contents[i].getCanonicalPath();
				if (!directoriesVisited.add(canonicalPath))
					continue;
			} catch (IOException exception) {
				Activator.logError(exception.getLocalizedMessage(), exception);

			}
