		long modified = directory.lastModified();
		if (path.equals(lastPath)
				&& lastModified == modified
				&& lastNestedProjects == nestedProjects
				&& lastCopyFiles == copyFiles)
		{
			return;
		}

		lastPath = path;
		lastModified = modified;
		lastNestedProjects = nestedProjects;
		lastCopyFiles = copyFiles;
