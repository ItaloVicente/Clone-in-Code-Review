		private IHyperlink createWorkingTreeLink(IRegion linkRegion,
				FileDiff diff, DiffEntry.Side side, int lineNo) {
			DiffEntry.ChangeType change = diff.getChange();
			if (change == DiffEntry.ChangeType.DELETE) {
				return null;
			}
			String path = diff.getNewPath();
			if (side == DiffEntry.Side.OLD) {
				switch (change) {
				case ADD:
					return null;
				case MODIFY:
					break;
				case RENAME:
				case COPY:
					path = diff.getOldPath();
					break;
				default:
					break;
				}
			}
			File file = new Path(
					diff.getRepository().getWorkTree().getAbsolutePath())
							.append(path).toFile();
			if (file.exists()) {
				return new FileLink(linkRegion, file, lineNo);
			}
			return null;
		}

