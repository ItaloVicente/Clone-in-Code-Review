			if (reftableConfig != null && !srcReftables.isEmpty()) {
				compactReftables(ctx);
			}
			compactPacks(ctx

			List<DfsPackDescription> commit = getNewPacks();
			Collection<DfsPackDescription> remove = toPrune();
			if (!commit.isEmpty() || !remove.isEmpty()) {
				objdb.commitPack(commit
			}
		} finally {
			rw = null;
		}
	}
