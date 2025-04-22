
	@Test
	public void testPruneOldOrphanCommit() throws Exception {
		StoredConfig config = git.getRepository().getConfig();
		config.setString("gc"
		config.setString("gc"
		config.save();
		ObjectId initial = git.getRepository().resolve("HEAD");
		RevCommit orphan = git.commit().setMessage("orphan").call();
		changeLastModified(orphan
		RefUpdate refUpdate = git.getRepository()
				.updateRef("refs/heads/master");
		refUpdate.setNewObjectId(initial);
		refUpdate.forceUpdate();
		FileUtils.delete(new File(git.getRepository().getDirectory()
				FileUtils.RECURSIVE | FileUtils.RETRY);

		git.gc().setExpire(new Date()).call();
		Thread.sleep(4000);
		git.gc().setExpire(new Date()).call();

		assertNull(git.getRepository().resolve(orphan.name()));
	}

	private static Date subtractDays(Date date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH
		return calendar.getTime();
	}

	private void changeLastModified(ObjectId commitId
		File objectsDirectory = new File(git.getRepository().getDirectory()
				"objects");
		File commitObjectDirectory = new File(objectsDirectory
				commitId.name().substring(0
		File commitObjectFile = new File(commitObjectDirectory
				commitId.name().substring(2));
		commitObjectFile.setLastModified(date.getTime());
	}

