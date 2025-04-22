		}
		if (gitdir == null)
			gitdir = new File(localName, Constants.DOT_GIT).getAbsolutePath();

		dst = new FileRepositoryBuilder().setGitDir(new File(gitdir)).build();
		dst.create();
		final StoredConfig dstcfg = dst.getConfig();
		dstcfg.setBoolean("core", null, "bare", false); //$NON-NLS-1$ //$NON-NLS-2$
		dstcfg.save();
		db = dst;

		outw.print(MessageFormat.format(
				CLIText.get().initializedEmptyGitRepositoryIn, gitdir));
		outw.println();
		outw.flush();
