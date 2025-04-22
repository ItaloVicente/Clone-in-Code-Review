		try (Git git = new Git(repo)) {
			Status status = git.status().call();
			assertThat(Long.valueOf(status.getModified().size()),
					is(Long.valueOf(1)));
			assertThat(status.getModified().iterator().next(),
					is(PROJ1 + "/" + FOLDER + "/" + FILE2));
		}
