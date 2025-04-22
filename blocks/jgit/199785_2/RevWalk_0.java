			try {
				commitGraph = reader != null
						? reader.getCommitGraph().orElse(EMPTY)
						: EMPTY;
			} catch (IOException e) {
				commitGraph = EMPTY;
			}
