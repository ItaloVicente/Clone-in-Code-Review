		boolean interrupted = false;
		try {
			FileSnapshot snapshot = FileSnapshot.save(finalPack);
			if (pconfig.isWaitToPreventRacyPack(snapshot.size())) {
				snapshot.waitUntilNotRacy();
			}
		} catch (InterruptedException e) {
			interrupted = true;
		}
