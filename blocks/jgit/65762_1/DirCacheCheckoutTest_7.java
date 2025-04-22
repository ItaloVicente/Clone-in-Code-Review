				} else if (file.isDirectory()) {
					if (file.list().length == 0) {
						assertEquals("found unexpected empty folder for path "
								+ path + " in workDir. "
						nrFiles++;
					}
				}
				if (walk.isSubtree()) {
					walk.enterSubtree();
