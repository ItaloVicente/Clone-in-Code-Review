			if (!postCloneTasks.isEmpty()) {
				progress.setWorkRemaining(postCloneTasks.size());
				progress.subTask(CoreText.CloneOperation_configuring);
				for (PostCloneTask task : postCloneTasks) {
					task.execute(repository, progress.newChild(1));
				}
