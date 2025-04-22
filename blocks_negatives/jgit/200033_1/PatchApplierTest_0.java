				preImage = IO
						.readWholeStream(getTestResource(name + "_PreImage"), 0)
						.array();
				try (Git git = new Git(db)) {
					Files.write(f.toPath(), preImage);
					git.add().addFilepattern(name).call();
				}
