				outw.println(objectId.name());
			}
		} else {
			if (verify && commits.size() > 1) {
				final CmdLineParser clp = new CmdLineParser(this);
				throw new CmdLineException(clp,
						CLIText.format(CLIText.get().needSingleRevision));
			}
