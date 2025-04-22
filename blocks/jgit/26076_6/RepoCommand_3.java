				Repository subRepo = add.call();
				if (revision != null) {
					Git sub = new Git(subRepo);
					sub.checkout().setName(findRef(revision
					git.add().addFilepattern(name).call();
				}
