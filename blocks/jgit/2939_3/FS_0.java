			String overrideGitPrefix = SystemReader.getInstance().getProperty(
					"jgit.gitprefix");
			if (overrideGitPrefix != null)
				p = new Holder<File>(new File(overrideGitPrefix));
			else
				p = new Holder<File>(discoverGitPrefix());
