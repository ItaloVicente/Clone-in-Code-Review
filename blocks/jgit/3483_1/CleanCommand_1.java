	public Status call() {
		if (retUntracked.isEmpty() == true)
			return null;

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		for (String i : retUntracked) {
			try {
				FileUtils.delete(new File(i)
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return new Status(diff);
	}

	public void setPaths(LinkedList<String> paths) {
		this.paths = paths;
	}

	public LinkedList<String> getPaths() {
		return paths;
	}
