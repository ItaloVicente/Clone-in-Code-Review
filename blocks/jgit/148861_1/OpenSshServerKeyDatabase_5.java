					if (Files.exists(path) || !askAboutNewFile
							|| ask.createNewFile(path)) {
						updateKnownHostsFile(candidates
								config);
						toUpdate.resetReloadAttributes();
					}
				} catch (Exception e) {
