			diffFmt.setRepository(repository);
			List<DiffEntry> diffs = diffFmt.scan(parents[0].getId(), commit.getId());
			for (DiffEntry ent : diffs) {
				String path;
				if (ChangeType.DELETE.equals(ent.getChangeType()))
					path = ent.getOldPath();
				else
					path = ent.getNewPath();
				currentEncoding = CompareCoreUtils.getResourceEncoding(repository, path);
				diffFmt.format(ent);
