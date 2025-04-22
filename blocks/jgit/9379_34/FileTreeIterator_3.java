			@SuppressWarnings("hiding")
			FileMode mode = null;
			try {
				if (fs.isSymLink(f)) {
					mode = FileMode.SYMLINK;
				} else if (fs.isDirectory(f)) {
					if (fs.exists(new File(f
						mode = FileMode.GITLINK;
					else
						mode = FileMode.TREE;
				} else if (fs.canExecute(file))
					mode = FileMode.EXECUTABLE_FILE;
