		final RevFlag inCachedPack = walker.newFlag("inCachedPack");
		final RevFlag include = walker.newFlag("include");

		final RevFlagSet keepOnRestart = new RevFlagSet();
		keepOnRestart.add(inCachedPack);

