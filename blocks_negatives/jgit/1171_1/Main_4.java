			if (gitdir == null) {
				String gitDirEnv = SystemReader.getInstance().getenv(Constants.GIT_DIR_KEY);
				if (gitDirEnv != null)
					gitdir = new File(gitDirEnv);
			}
			if (gitdir == null)
				gitdir = findGitDir();

			File gitworktree;
			String gitWorkTreeEnv = SystemReader.getInstance().getenv(Constants.GIT_WORK_TREE_KEY);
			if (gitWorkTreeEnv != null)
				gitworktree = new File(gitWorkTreeEnv);
			else
				gitworktree = null;

			File indexfile;
			String indexFileEnv = SystemReader.getInstance().getenv(Constants.GIT_INDEX_KEY);
			if (indexFileEnv != null)
				indexfile = new File(indexFileEnv);
			else
				indexfile = null;

			File objectdir;
			String objectDirEnv = SystemReader.getInstance().getenv(Constants.GIT_OBJECT_DIRECTORY_KEY);
			if (objectDirEnv != null)
				objectdir = new File(objectDirEnv);
			else
				objectdir = null;

			File[] altobjectdirs;
			String altObjectDirEnv = SystemReader.getInstance().getenv(Constants.GIT_ALTERNATE_OBJECT_DIRECTORIES_KEY);
			if (altObjectDirEnv != null) {
				String[] parserdAltObjectDirEnv = altObjectDirEnv.split(File.pathSeparator);
				altobjectdirs = new File[parserdAltObjectDirEnv.length];
				for (int i = 0; i < parserdAltObjectDirEnv.length; i++)
					altobjectdirs[i] = new File(parserdAltObjectDirEnv[i]);
			} else
				altobjectdirs = null;

			if (gitdir == null || !gitdir.isDirectory()) {
