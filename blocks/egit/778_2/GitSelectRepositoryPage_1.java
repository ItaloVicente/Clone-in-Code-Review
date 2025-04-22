
					Set<String> dirs = dlg.getDirectories();
					for (String dir : dirs)
						util.addConfiguredRepository(new File(dir));

					tv.setInput(util.getConfiguredRepositories());
					checkPage();

