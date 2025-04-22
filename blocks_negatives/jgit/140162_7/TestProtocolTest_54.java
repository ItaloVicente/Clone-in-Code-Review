			git.fetch()
					.setRemote(uri.toString())
					.setRefSpecs(HEADS)
					.call();
			assertEquals(master,
					local.getRepository().exactRef("refs/heads/master").getObjectId());
