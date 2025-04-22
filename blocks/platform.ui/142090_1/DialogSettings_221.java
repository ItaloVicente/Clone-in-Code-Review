		}

		public XMLWriter(Writer output) throws IOException {
			super(output);
			tab = 0;
			writeln(XML_VERSION);
		}
