				Repository subRepo = add.call();
				if (revision != null) {
					Git git = new Git(subRepo);
					git.checkout().setName(findRef(revision
					git.add().addFilepattern(name).call();
				}
