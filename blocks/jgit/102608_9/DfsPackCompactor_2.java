				PackStatistics stats = pw.getStatistics();
				pw.close();
				pw = null;

				outDesc.setPackStats(stats);
				newStats = stats;
				rollback = false;
