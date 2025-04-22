			} else
				switch (listMode) {
				case REMOTE:
					refs.addAll(
							repo.getRefDatabase().getRefsByPrefix(R_REMOTES));
					break;
				default:
					refs.addAll(repo.getRefDatabase().getRefsByPrefix(R_HEADS
							R_REMOTES));
					break;
				}
