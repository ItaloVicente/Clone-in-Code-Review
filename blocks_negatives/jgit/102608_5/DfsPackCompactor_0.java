				pw.setDeltaBaseAsOffset(true);
				pw.setReuseDeltaCommits(false);

				addObjectsToPack(pw, ctx, pm);
				if (pw.getObjectCount() == 0) {
					List<DfsPackDescription> remove = toPrune();
					if (remove.size() > 0)
						objdb.commitPack(
								Collections.<DfsPackDescription>emptyList(),
								remove);
					return;
				}
