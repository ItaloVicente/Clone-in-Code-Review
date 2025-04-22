		}

		public XMLWriter(Writer output) throws IOException {
			super(output);
			tab = 0;
			writeln(XML_VERSION);
		}

		private void writeln(String text) throws IOException {
			write(text);
			newLine();
		}

		public void endTag(String name) throws IOException {
			tab--;
			printTag("/" + name, null, false); //$NON-NLS-1$
		}

		private void printTabulation() throws IOException {
			for (int i = 0; i < tab; i++) {
