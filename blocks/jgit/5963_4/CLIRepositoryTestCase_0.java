
	protected String[] executeAndPrint(String... cmds) throws Exception {
		String[] lines = execute(cmds);
		for (String line : lines) {
			System.out.println(line);
		}
		return lines;
	}

	protected String[] executeAndPrintTestCode(String... cmds) throws Exception {
		String[] lines = execute(cmds);
		String cmdString = cmdString(cmds);
		if (lines.length == 0)
			System.out.println("\t\tassertTrue(execute(" + cmdString
					+ ").length == 0);");
		else {
			System.out
			System.out.print("\t\t\t\t\t\t\"" + escapeJava(lines[0]));
			for (int i=1; i<lines.length; i++) {
				System.out.println("\"
				System.out.print("\t\t\t\t\t\t\"" + escapeJava(lines[i]));
			}
			System.out.println("\t\t\t\t}
		}
		return lines;
	}

	protected String cmdString(String... cmds) {
		if (cmds.length == 0)
			return "";
		else if (cmds.length == 1)
			return "\"" + escapeJava(cmds[0]) + "\"";
		else {
			StringBuilder sb = new StringBuilder(cmdString(cmds[0]));
			for (int i=1; i<cmds.length; i++) {
				sb.append("
				sb.append(cmdString(cmds[i]));
			}
			return sb.toString();
		}
	}

	protected String escapeJava(String line) {
		return line.replaceAll("\""
				.replaceAll("\\\\"
				.replaceAll("\t"
	}

	protected void assertArrayOfLinesEquals(String[] expected
		assertEquals(toText(expected)
	}

	private String toText(String[] lines) {
		StringBuilder b = new StringBuilder();
		for (String s : lines) {
			b.append(s);
			b.append('\n');
		}
		return b.toString();
	}
