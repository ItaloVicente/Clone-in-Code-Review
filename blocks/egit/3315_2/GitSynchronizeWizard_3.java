			GitSynchronizeDataSet syncData = page1.getSyncData();
			List<IProject> projects = page1.getProjects();
			syncData.addAll(page2.getSyncData());
			projects.addAll(page2.getProjects());
			GitModelSynchronize.launch(syncData,
					projects.toArray(new IProject[projects.size()]));
