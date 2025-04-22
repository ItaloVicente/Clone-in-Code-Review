			if (messages != null) {
				msgs = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(messages, Constants.CHARSET),
						8192)) {
					@Override
					public void println() {
						print('\n');
					}
				};
			}
