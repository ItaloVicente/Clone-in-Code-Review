					Repository repository = getRepository();
					if (repository == null) {
						return Status.CANCEL_STATUS;
					}
					IndexDiffData result = calcIndexDiffDataFull(monitor,
							getName(), repository);
