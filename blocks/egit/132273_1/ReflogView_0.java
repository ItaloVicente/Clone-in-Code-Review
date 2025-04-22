				Repository repo = oldInput.getRepository();
				if (repo.getDirectory()
						.equals(event.getRepository().getDirectory())) {
					try {
						if (repo.findRef(oldInput.getRef()) != null) {
							refLogTableTreeViewer.setInput(
									new ReflogInput(oldInput.getRepository(),
											oldInput.getRef()));
							return;
						}
					} catch (IOException e) {
					}
					showReflogFor(repo);
				}
