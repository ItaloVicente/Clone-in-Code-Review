				Repository subRepo = add.call();
				if (revision != null) {
					Git git = new Git(subRepo);
					if (!ObjectId.isId(revision)) {
						Ref ref = subRepo.getRef(
								Constants.DEFAULT_REMOTE_NAME + "/" + revision);
						if (ref != null)
							revision = ref.getName();
					}
					git.checkout().setName(revision).call();
					git.add().addFilepattern(name).call();
				}
