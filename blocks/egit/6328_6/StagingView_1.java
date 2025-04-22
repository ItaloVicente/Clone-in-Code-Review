
					if (!addPaths.isEmpty())
						try {
							AddCommand add = git.add();
							for (String addPath : addPaths)
								add.addFilepattern(addPath);
							add.call();
						} catch (NoFilepatternException e1) {
						} catch (Exception e2) {
							Activator.error(e2.getMessage(), e2);
						}
					if (rm != null)
						try {
							rm.call();
						} catch (NoFilepatternException e) {
						} catch (Exception e2) {
							Activator.error(e2.getMessage(), e2);
						}
				} finally {
					asyncUnlockUI();
