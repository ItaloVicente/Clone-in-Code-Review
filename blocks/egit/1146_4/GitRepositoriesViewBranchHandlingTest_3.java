			for (int i = 0; i < 1000; i++) {
				if (done[0])
					break;
				Thread.sleep(10);
			}

			GitLightweightDecorator.refresh();

			assertTrue("Branch should not be symbolic", ObjectId
					.isId(lookupRepository(clonedRepositoryFile).getBranch()));
