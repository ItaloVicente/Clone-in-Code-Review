		boolean interrupted = false;
		try {
			FileSnapshot snapshot = FileSnapshot.save(realPack);
			if (pconfig.isWaitToPreventRacyPack(snapshot.size())) {
				snapshot.waitUntilNotRacy();
			}
		} catch (InterruptedException e) {
			interrupted = true;
		}
		try {
			db.openPack(realPack);
			rollback = false;
		} finally {
			clear();
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
