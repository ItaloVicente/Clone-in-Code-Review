		try (Git git = new Git(db)) {
			if (preExists) {
				a = new RawText(readFile(name + "_PreImage"));
				write(new File(db.getDirectory().getParent()
						a.getString(0

				git.add().addFilepattern(name).call();
				git.commit().setMessage("PreImage").call();
			}

			if (postExists) {
				b = new RawText(readFile(name + "_PostImage"));
			}

			return git
					.apply()
					.setPatch(getTestResource(name + ".patch")).call();
