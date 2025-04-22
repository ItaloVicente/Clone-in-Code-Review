		if (tagName != null) {
			createTag(git);
		} else {
			listTags(git);
		}
	}

	private void listTags(Git git) {
		List<RevTag> tagList = git.tagList().call();
		for (RevTag tag : tagList) {
			out.println(tag.getTagName());
		}

	}

	private void createTag(Git git) throws Exception
