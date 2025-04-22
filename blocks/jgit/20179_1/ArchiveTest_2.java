		for (Format fmt : formats) {
			byte[] result = CLIGitCommand.rawExecute(
					fmt.cmd() + "--prefix=my- master"
			String[] expect = { "my-baz"
			String[] actual = fmt.listEntries(result);

			Arrays.sort(expect);
			Arrays.sort(actual);
			assertArrayEquals(expect
		}
