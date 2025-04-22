			if (err.isAborted())
				System.exit(1);
			System.err.println(CLIText.fatalError(err.getMessage()));
			if (showStackTrace)
				err.printStackTrace();
			System.exit(128);
