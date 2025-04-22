
				fetch.execute(fetchArgs.toArray(new String[0]));

				fetch.outw.flush();
				fetch.errw.flush();
				outw.println(osw.toString());
				errw.println(esw.toString());
			} else {
				throw new JGitInternalException(MessageFormat
						.format(CLIText.get().unknownSubcommand, command));
			}
