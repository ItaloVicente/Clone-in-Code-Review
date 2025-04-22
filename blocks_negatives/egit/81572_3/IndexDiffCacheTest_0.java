		IndexDiffData data1 = waitForListenerCalled();
		assertThat(data1.getIgnoredNotInIndex(), hasItem("Project-1/ignore"));

		project.createFolder("sub/ignore");

		IndexDiffData data2 = waitForListenerCalled();
		assertThat(data2.getIgnoredNotInIndex(), hasItem("Project-1/ignore"));
		assertThat(data2.getIgnoredNotInIndex(), hasItem("Project-1/sub/ignore"));

