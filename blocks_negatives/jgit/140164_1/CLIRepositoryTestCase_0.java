		if (cmds.length == 0)
			return "";
		else if (cmds.length == 1)
			return "\"" + escapeJava(cmds[0]) + "\"";
		else {
			StringBuilder sb = new StringBuilder(cmdString(cmds[0]));
			for (int i=1; i<cmds.length; i++) {
				sb.append(", ");
				sb.append(cmdString(cmds[i]));
			}
			return sb.toString();
		}
