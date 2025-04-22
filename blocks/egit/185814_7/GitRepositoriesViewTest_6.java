			@Override
			public boolean test() throws Exception {
				SWTBotTreeItem item = findWorkdirNode(tree, PROJ2, FOLDER);
				return item.getNodes().size() == n - 1;
			}

			@Override
			public String getFailureMessage() {
				return "File deletion not reflected in repo view tree within timout";
			}
		}, 20000);
