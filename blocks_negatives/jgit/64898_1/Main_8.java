			if (err.isAborted())
				System.exit(1);
			System.err.println(MessageFormat.format(CLIText.get().fatalError, err.getMessage()));
			if (showStackTrace)
				err.printStackTrace();
			System.exit(128);
