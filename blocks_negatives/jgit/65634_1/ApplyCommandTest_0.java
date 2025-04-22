		Git git = new Git(db);

		if (preExists) {
			a = new RawText(readFile(name + "_PreImage"));
			write(new File(db.getDirectory().getParent(), name),
					a.getString(0, a.size(), false));

			git.add().addFilepattern(name).call();
			git.commit().setMessage("PreImage").call();
