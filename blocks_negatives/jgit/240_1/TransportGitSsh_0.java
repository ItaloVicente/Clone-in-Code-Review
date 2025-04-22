			String path = uri.getPath();
			if (uri.getScheme() != null && uri.getPath().startsWith("/~"))
				path = (uri.getPath().substring(1));

			final StringBuilder cmd = new StringBuilder();
			final int gitspace = exe.indexOf("git ");
			if (gitspace >= 0) {
				sqMinimal(cmd, exe.substring(0, gitspace + 3));
				cmd.append(' ');
				sqMinimal(cmd, exe.substring(gitspace + 4));
			} else
				sqMinimal(cmd, exe);
			cmd.append(' ');
			sqAlways(cmd, path);
			channel.setCommand(cmd.toString());
