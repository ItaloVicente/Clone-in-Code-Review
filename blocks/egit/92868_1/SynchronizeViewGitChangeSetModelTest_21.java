		try (Git git = new Git(repo)) {
			Status status = git.status().call();
			assertThat(Long.valueOf(status.getChanged().size()),
					is(Long.valueOf(1L)));
			assertThat(status.getChanged().iterator().next(),
					is(PROJ1 + "/" + FOLDER + "/" + FILE1));
		}
