			if (attributes.isSymbolicLink())
				mode = FileMode.SYMLINK;
			else if (attributes.isDirectory()) {
				if (new File(f, Constants.DOT_GIT).exists())
					mode = FileMode.GITLINK;
				else
					mode = FileMode.TREE;
			} else if (attributes.isExecutable())
				mode = FileMode.EXECUTABLE_FILE;
			else
				mode = FileMode.REGULAR_FILE;
