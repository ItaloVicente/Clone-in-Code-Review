			@Override
			public void run() {
				String errorMessage = null;

				Clipboard clip = null;
				try {
					clip = new Clipboard(getSite().getShell().getDisplay());
					String content = (String) clip.getContents(TextTransfer
							.getInstance());
					if (content == null) {
						errorMessage = UIText.RepositoriesView_NothingToPasteMessage;
						return;
					}
