		for (Format fmt : formats) {
			byte[] result = CLIGitCommand.rawExecute(
					fmt.cmd() + "--prefix=x/ master"
			String[] expect = { "x/baz"
			String[] actual = fmt.listEntries(result);

			Arrays.sort(expect);
			Arrays.sort(actual);
			assertArrayEquals(expect
		}
