			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			git1.push().setRemote("test").setRefSpecs(spec).call();
			assertEquals("1:test
					+ commit.getName() + " refs/heads/x "
					+ ObjectId.zeroId().name()
		}
