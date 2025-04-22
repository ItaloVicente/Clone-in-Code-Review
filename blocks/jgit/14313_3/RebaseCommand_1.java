	private interface StepFormatter<T> {
		public static final StepFormatter<Step> STEP_FORMATTER = new StepFormatter<Step>() {

			public String getToken(Step obj) {
				return obj.action.token;
			}

			public String getName(Step obj) {
				return obj.commit.name();
			}

			public String getShortMessage(Step obj) {
				return RawParseUtils.decode(obj.shortMessage);
			}
		};

		String getToken(T obj) throws IOException;

		String getName(T obj) throws IOException;

		String getShortMessage(T obj) throws IOException;
	}

	public void writeSteps(List<Step> steps) throws IOException
			WrongRepositoryStateException {
		if (repo.getRepositoryState() != RepositoryState.REBASING_INTERACTIVE)
			throw new WrongRepositoryStateException(MessageFormat.format(
					JGitText.get().wrongRepositoryState
							.getRepositoryState().name()));
		writeSteps(null
	}

	private <T> void writeSteps(final String headComment
			StepFormatter<T> formatter) throws
			FileNotFoundException
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(rebaseState.getFile(GIT_REBASE_TODO))
				Constants.CHARACTER_ENCODING));
		try {
			if (headComment != null) {
				fw.write(headComment);
				fw.newLine();
			}
			for (T step : steps) {
				if (step == null)
					continue;
				fw.append(formatter.getToken(step));
				fw.append(formatter.getName(step));
				fw.append(formatter.getShortMessage(step));
				fw.newLine();
			}
		} finally {
			fw.close();
		}
	}

