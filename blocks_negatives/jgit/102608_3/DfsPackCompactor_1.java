				boolean rollback = true;
				DfsPackDescription pack = objdb.newPack(COMPACT,
						estimatePackSize());
				try {
					writePack(objdb, pack, pw, pm);
					writeIndex(objdb, pack, pw);

					PackStatistics stats = pw.getStatistics();
					pw.close();
					pw = null;

					pack.setPackStats(stats);
					objdb.commitPack(Collections.singletonList(pack), toPrune());
					newPacks.add(pack);
					newStats.add(stats);
					rollback = false;
				} finally {
					if (rollback)
						objdb.rollbackPack(Collections.singletonList(pack));
				}
