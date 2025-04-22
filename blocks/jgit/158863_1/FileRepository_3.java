
			if (writeLogs) {
				List<ReflogEntry> logs = oldDb.getReflogReader(r.getName())
					.getReverseEntries();
				Collections.reverse(logs);
				for (ReflogEntry e : logs) {
					logWriter.log(r.getName()
				}
			}
