			if (err.isAborted()) {
				exit(1
			}
			writer.println(CLIText.fatalError(err.getMessage()));
			if (showStackTrace) {
				err.printStackTrace(writer);
			}
			exit(128
