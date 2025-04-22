				Map<String, ITool> userTools = diffToolMgr
						.getUserDefinedTools();
				for (String name : userTools.keySet()) {
							+ userTools.get(name).getCommand());
				}
				outw.println(
						"The following tools are valid, but not currently available:"); //$NON-NLS-1$
				for (String name : diffToolMgr.getNotAvailableTools()
						.keySet()) {
				}
				outw.println(
						"environment. If run in a terminal-only session, they will fail."); //$NON-NLS-1$
				return;
			}
			diffFmt.setRepository(db);
			if (cached) {
				if (oldTree == null) {
					if (head == null) {
						die(MessageFormat.format(CLIText.get().notATree, HEAD));
