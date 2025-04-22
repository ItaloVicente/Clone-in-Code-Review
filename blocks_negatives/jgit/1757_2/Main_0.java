		if (cmd.requiresRepository()) {
					.findGitDir();
			if (rb.getGitDir() == null) {
				writer.println(CLIText.get().cantFindGitDirectory);
				writer.flush();
				System.exit(1);
			}

			cmd.init(rb.build(), null);
		} else {
			cmd.init(null, gitdir);
		}
