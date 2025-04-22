		if (System.out.checkError()) {
			System.err.println(CLIText.get().unknownIoErrorStdout);
			System.exit(1);
		}
		if (System.err.checkError()) {
			System.exit(1);
		}
