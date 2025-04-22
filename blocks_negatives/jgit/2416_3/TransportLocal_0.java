			final String[] args;

			if (cmd.startsWith("git-")) {
				args = new String[] { "git", cmd.substring(4), PWD };
			} else {
				final int gitspace = cmd.indexOf("git ");
				if (gitspace >= 0) {
					final String git = cmd.substring(0, gitspace + 3);
					final String subcmd = cmd.substring(gitspace + 4);
					args = new String[] { git, subcmd, PWD };
				} else {
					args = new String[] { cmd, PWD };
				}
			}

			return Runtime.getRuntime().exec(args, null, remoteGitDir);
