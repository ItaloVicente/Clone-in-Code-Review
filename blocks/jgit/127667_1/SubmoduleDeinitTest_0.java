		try (Repository submoduleLocalRepo = generator.getRepository()) {
			JGitTestUtil.writeTrashFile(submoduleLocalRepo
					"new data");
			Git.wrap(submoduleLocalRepo).commit().setAll(true)
					.setMessage("local commit").call();
		}
