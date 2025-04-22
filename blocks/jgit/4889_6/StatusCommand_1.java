	public static void main(String[] args) throws Exception {
		FileRepository repo = new FileRepository(
				"/home/kevin/test-repositories/jquery/.git");
		Status status = new StatusCommand(repo).call();

		System.out.println("added");
		for (String add : status.getAdded())
			System.out.println(" " + add);

		System.out.println("changed");
		for (String add : status.getChanged())
			System.out.println(" " + add);

		System.out.println("conflicting");
		for (String add : status.getConflicting())
			System.out.println(" " + add);

		System.out.println("missing");
		for (String add : status.getMissing())
			System.out.println(" " + add);

		System.out.println("modified");
		for (String add : status.getModified())
			System.out.println(" " + add);

		System.out.println("removed");
		for (String add : status.getRemoved())
			System.out.println(" " + add);

		System.out.println("untracked");
		for (String add : status.getUntracked())
			System.out.println(" " + add);
	}

