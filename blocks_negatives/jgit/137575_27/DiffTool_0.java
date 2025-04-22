				String notAvailableToolNames = new String();
				for (String name : diffTools.getNotAvailableTools().keySet()) {
					notAvailableToolNames += String.format("\t\t{0}\n", name); //$NON-NLS-1$
				}
				String userToolNames = new String();
				Map<String, ExternalDiffTool> userTools = diffTools
						.getUserDefinedTools();
				for (String name : userTools.keySet()) {
					availableToolNames += String.format("\t\t{0}.cmd {1}\n", //$NON-NLS-1$
							name,
							userTools.get(name).getCommand());
				}
				outw.println(MessageFormat.format(
						CLIText.get().diffToolHelpSetToFollowing,
						availableToolNames, userToolNames,
						notAvailableToolNames));
				return;
			}
			diffFmt.setRepository(db);
			if (cached) {
				if (oldTree == null) {
					if (head == null) {
						die(MessageFormat.format(CLIText.get().notATree, HEAD));
