			try {
				Repository subRepo = add.call();
				if (revision != null) {
					try (Git sub = new Git(subRepo)) {
						sub.checkout().setName(findRef(revision, subRepo))
								.call();
					}
					subRepo.close();
					git.add().addFilepattern(name).call();
