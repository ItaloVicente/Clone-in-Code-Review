					boolean foundNew = false;

					for (String foundDir : directories) {
						if (!existingRepositoryDirs.contains(foundDir)) {
							foundNew = true;
							break;
						}
					}

					btnToggleSelect.setEnabled(foundNew);
					tab.setEnabled(directories.size() > 0);
