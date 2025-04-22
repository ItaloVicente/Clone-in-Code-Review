			String content = (String) clipboard.getContents(TextTransfer
					.getInstance());
			if (content == null)
				MessageDialog
						.openConfirm(
								getShell(),
								UIText.SimpleConfigureFetchDialog_NothingToPasteMessage,
								UIText.SimpleConfigureFetchDialog_EmptyClipboardMessage);
			try {
				RefSpec spec = new RefSpec(content);
				Ref source;
				try {
					source = repository.findRef(spec.getDestination());
				} catch (IOException e1) {
					source = null;
