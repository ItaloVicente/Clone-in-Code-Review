
	@Test
	public void callSignerWithProperSigningKey() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();

			String[] signingKey = new String[1];
			AtomicInteger callCount = new AtomicInteger();
			GpgSigner.setDefault(new GpgSigner() {
				@Override
				public void sign(CommitBuilder commit
					signingKey[0] = gpgSigningKey;
					callCount.incrementAndGet();
				}
			});

			git.commit().setSign(Boolean.TRUE).setMessage("initial commit")
					.call();
			assertNull(signingKey[0]);
			assertEquals(1

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();

			String expectedConfigSigningKey = "config-" + System.nanoTime();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("user"
					expectedConfigSigningKey);
			config.save();

			git.commit().setSign(Boolean.TRUE).setMessage("initial commit")
					.call();
			assertEquals(expectedConfigSigningKey
			assertEquals(2

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();

			String expectedSigningKey = "my-" + System.nanoTime();
			git.commit().setSign(Boolean.TRUE).setSigningKey(expectedSigningKey)
					.setMessage("initial commit").call();
			assertEquals(expectedSigningKey
			assertEquals(3
		}
	}

	@Test
	public void callSignerOnlyWhenSigning() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();

			AtomicInteger callCount = new AtomicInteger();
			GpgSigner.setDefault(new GpgSigner() {
				@Override
				public void sign(CommitBuilder commit
					callCount.incrementAndGet();
				}
			});

			git.commit().setMessage("initial commit").call();
			assertEquals(0

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();

			git.commit().setSign(Boolean.TRUE).setMessage("commit").call();
			assertEquals(1

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();

			StoredConfig config = git.getRepository().getConfig();
			config.setBoolean("commit"
			config.save();

			git.commit().setMessage("commit").call();
			assertEquals(2

			writeTrashFile("file4"
			git.add().addFilepattern("file4").call();

			git.commit().setSign(Boolean.FALSE).setMessage("commit").call();
			assertEquals(2
		}
	}
