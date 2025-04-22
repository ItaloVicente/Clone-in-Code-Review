	}

	private Git createTestRepo(int testDataSeed
			throws IOException
			NoHeadException
			ConcurrentRefUpdateException
			AbortedByHookException {
		Random r = new Random(testDataSeed);
		Git git = Git.wrap(db);
		File f = writeTrashFile("file"
		appendRandomLine(f
		git.add().addFilepattern("file").call();
		git.commit().setMessage("message1").call().getId();
		appendRandomLine(f
		git.add().addFilepattern("file").call();
		git.commit().setMessage("message2").call().getId();
		return git;
	}

	@Test
	public void testDetetctModificationAlthoughtSameSizeAndModificationtime()
			throws Exception {
		int testDataSeed = 1;
		int testDataLength = 100;
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		config.save();

		createTestRepo(testDataSeed

		PackFile pf = repackAndCheck(5
		Path packFilePath = pf.getPackFile().toPath();
		AnyObjectId chk1 = pf.getPackChecksum();
		String name = pf.getPackName();
		Long length = Long.valueOf(pf.getPackFile().length());
		long m1 = packFilePath.toFile().lastModified();

		long timerResolution = FS.getFsTimerResolution(packFilePath).toMillis();
		waitForTimerTick(packFilePath

		AnyObjectId chk2 = repackAndCheck(6
				.getPackChecksum();
		long m2 = packFilePath.toFile().lastModified();
		assumeFalse(m2 == m1);

		AnyObjectId chk3 = repackAndCheck(7
				.getPackChecksum();
		long m3 = packFilePath.toFile().lastModified();

		db.getObjectDatabase().has(unknownID);
		assertEquals(chk3
				.getPackChecksum());
		assumeTrue(m3 == m2);
	}

	@Test
	public void testDetetctModificationAlthoughtSameSizeAndModificationtimeAndFileKey()
			throws Exception {
		int testDataSeed = 1;
		int testDataLength = 100;
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		config.save();

		createTestRepo(testDataSeed

		PackFile pf = repackAndCheck(5
		Path packFilePath = pf.getPackFile().toPath();
		Path packFileBasePath = packFilePath.resolveSibling(
				packFilePath.getFileName().toString().replaceAll(".pack"
		AnyObjectId chk1 = pf.getPackChecksum();
		String name = pf.getPackName();
		Long length = Long.valueOf(pf.getPackFile().length());
		copyPack(packFileBasePath

		AnyObjectId chk2 = repackAndCheck(6
				.getPackChecksum();
		copyPack(packFileBasePath

		AnyObjectId chk3 = repackAndCheck(7
				.getPackChecksum();
		long m3 = packFilePath.toFile().lastModified();
		db.getObjectDatabase().has(unknownID);
		assertEquals(chk3
				.getPackChecksum());

		long timerResolution = FS.getFsTimerResolution(packFilePath).toMillis();
		waitForTimerTick(packFilePath

		copyPack(packFileBasePath
		long m2 = packFilePath.toFile().lastModified();
		assumeFalse(m3 == m2);

		db.getObjectDatabase().has(unknownID);
		assertEquals(chk2
				.getPackChecksum());

		copyPack(packFileBasePath
		long m1 = packFilePath.toFile().lastModified();
		assumeTrue(m2 == m1);
		db.getObjectDatabase().has(unknownID);
		assertEquals(chk1
				.getPackChecksum());
	}

	private Path copyFile(Path src
		if (Files.exists(dst)) {
			dst.toFile().setWritable(true);
			try (OutputStream dstOut = Files.newOutputStream(dst)) {
				Files.copy(src
				return dst;
			}
		} else {
			return Files.copy(src
		}
	}

	private Path copyPack(Path base
			throws IOException {
		copyFile(Paths.get(base + ".idx" + srcSuffix)
				Paths.get(base + ".idx" + dstSuffix));
		copyFile(Paths.get(base + ".bitmap" + srcSuffix)
				Paths.get(base + ".bitmap" + dstSuffix));
		return copyFile(Paths.get(base + ".pack" + srcSuffix)
				Paths.get(base + ".pack" + dstSuffix));
	}

	private PackFile repackAndCheck(int compressionLevel
			Long oldLength
			throws IOException
		PackFile p = getSinglePack(gc(compressionLevel));
		File pf = p.getPackFile();
		assumeTrue(oldLength == null || pf.length() == oldLength.longValue());
		assumeTrue(oldChkSum == null || !p.getPackChecksum().equals(oldChkSum));
		assertTrue(oldName == null || p.getPackName().equals(oldName));
		return p;
	}



	private void waitForTimerTick(Path p
			throws IOException
		long wakeup = Files.getLastModifiedTime(p).toMillis() + timerResolution;
		Thread.sleep(10 + wakeup - System.currentTimeMillis());
	}

	private PackFile getSinglePack(Collection<PackFile> packs) {
		Iterator<PackFile> pIt = packs.iterator();
		PackFile p = pIt.next();
		assertFalse(pIt.hasNext());
		return p;
