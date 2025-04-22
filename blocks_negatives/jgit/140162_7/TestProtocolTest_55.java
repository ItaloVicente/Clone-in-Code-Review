			git.push()
					.setRemote(uri.toString())
					.setRefSpecs(HEADS)
					.call();
			assertEquals(master,
					remote.getRepository().exactRef("refs/heads/master").getObjectId());
