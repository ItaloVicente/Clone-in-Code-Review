
	private void handleShallowUnshallowLines()
			throws IOException {
		System.out.println(
				"handleShallowUnshallowLines: begin!");
		String line = null;
		int length = -1;
		do {
			line = pckIn.readString();
			System.out
					.println("handleShallowUnshallowLines.line='" + line + "'");
			length = line.length();
			System.out.println(
					"handleShallowUnshallowLines.length='" + length + "'");
			if (length > 0) {
					final String id = line.substring(4
					System.out.println(
							"handleShallowUnshallowLines.id='" + id + "'");
					final String id = line.substring(4
					System.out.println(
							"handleShallowUnshallowLines.id='" + id + "'");
				} else {
					throw new PackProtocolException(MessageFormat.format(
							JGitText.get().expectedShallowUnshallowGot
				}
			}

		} while (length != 0);
		System.out.println("handleShallowUnshallowLines: end!");
	}

