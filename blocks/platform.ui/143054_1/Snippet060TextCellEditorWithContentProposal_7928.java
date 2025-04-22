			IContentProposalProvider contentProposalProvider = new SimpleContentProposalProvider("red", "green",
					"blue");
			KeyStroke keystroke = null;
			try {
				keystroke = KeyStroke.getInstance("Ctrl+Space");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String backspace = "\b"; //$NON-NLS-1$
			String delete = "\u007F"; //$NON-NLS-1$
			char[] autoactivationChars = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" + backspace //$NON-NLS-1$
					+ delete).toCharArray();
			cellEditor = new TextCellEditorWithContentProposal(viewer.getTable(), contentProposalProvider, keystroke,
					autoactivationChars);
