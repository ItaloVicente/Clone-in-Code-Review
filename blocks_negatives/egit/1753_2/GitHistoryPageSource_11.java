		final ResourceList input;

		if (object instanceof ResourceList)
			input = (ResourceList) object;
		else if (object instanceof IResource)
			input = new ResourceList(new IResource[] { (IResource) object });
		else
			input = new ResourceList(new IResource[0]);

		final GitHistoryPage pg = new GitHistoryPage();
		pg.setInput(input);
		return pg;
