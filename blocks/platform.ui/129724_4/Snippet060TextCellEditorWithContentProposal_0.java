			KeyStroke keystroke = null;
			try {
				keystroke = KeyStroke.getInstance("Ctrl+Space");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			char[] autoActivationCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\b"
					.toCharArray();
			cellEditor = new TextCellEditorWithContentProposal(viewer.getTable(), contentProposalProvider, keystroke,
					autoActivationCharacters);
