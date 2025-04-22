	public RebaseResult rewordAndContinue(RevCommit commit
			throws GitAPIException
			WrongRepositoryStateException {

		new Git(repo).commit().setMessage(newMessage).setAmend(true).call();
		return new Git(repo).rebase().setOperation(Operation.CONTINUE).call();
	}

	public interface ToDoLineFormatter<T> {
		public static final ToDoLineFormatter<Step> STEP_FORMATTER = new ToDoLineFormatter<Step>() {

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

	public RebaseCommand writeSteps(final String headComment
			throws FileNotFoundException
		writeSteps(headComment
		return this;
	}

	public <T> void writeSteps(final String headComment
			ToDoLineFormatter<T> formatter) throws
			FileNotFoundException
		if (formatter == null)
			throw new IllegalArgumentException(
					"ToDoLineFormatter must not be null");
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(rebaseState.getFile(GIT_REBASE_TODO))
				Constants.CHARACTER_ENCODING));
		try {
			if (headComment != null) {
				fw.write(headComment);
				fw.newLine();
			}
			StringBuilder sb = new StringBuilder();
			for (T step : steps) {
				sb.setLength(0);
				sb.append(formatter.getToken(step));
				sb.append(formatter.getName(step));
				sb.append(formatter.getShortMessage(step));
				fw.write(sb.toString());
				fw.newLine();
			}
		} finally {
			fw.close();
		}
	}

	public List<Step> readSteps() throws IOException {
		try {
			return loadSteps();
		} catch (FileNotFoundException e) {
			return null;
		}
	}

