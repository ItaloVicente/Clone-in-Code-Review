		final int gitspace = exe.indexOf("git ");
		if (gitspace >= 0) {
			sqMinimal(cmd, exe.substring(0, gitspace + 3));
			cmd.append(' ');
			sqMinimal(cmd, exe.substring(gitspace + 4));
		} else
			sqMinimal(cmd, exe);
