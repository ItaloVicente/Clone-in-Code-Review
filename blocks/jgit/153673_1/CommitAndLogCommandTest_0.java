			Path includeSubdir = Paths.get(db.getWorkTree().toString()
			includeSubdir.toFile().mkdirs();
			file = Paths.get(includeSubdir.toString()
			FileUtils.createNewFile(file);
			try (PrintWriter writer = new PrintWriter(file
				writer.print("content3");
			}

			git.add().addFilepattern("subdir-include").call();
			git.commit().setMessage("commit3").setCommitter(committer).call();

			Path excludeSubdir = Paths.get(db.getWorkTree().toString()
			excludeSubdir.toFile().mkdirs();
			file = Paths.get(excludeSubdir.toString()
			FileUtils.createNewFile(file);
			try (PrintWriter writer = new PrintWriter(file
				writer.print("content4");
			}

			git.add().addFilepattern("subdir-exclude").call();
			git.commit().setMessage("commit4").setCommitter(committer).call();

