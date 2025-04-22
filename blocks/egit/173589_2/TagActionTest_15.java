			someLightTagCommit = repo.exactRef(Constants.HEAD).getObjectId();
			git.tag()
				.setAnnotated(false)
				.setName("SomeLightTag")
				.setObjectId(repo.parseCommit(someLightTagCommit))
				.setSigned(false)
				.call();
		}
