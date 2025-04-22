	public CommitProposalProcessor(String[] messages, String[] paths) {
		if (messages != null && messages.length > 0)
			this.messages = new TreeSet<String>(Arrays.asList(messages));
		else
			this.messages = Collections.emptySet();

