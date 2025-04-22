				try {
					clipboard.setContents(new String[] { content },
							new Transfer[] { plainTextTransfer });
				} finally {
					clipboard.dispose();
				}
