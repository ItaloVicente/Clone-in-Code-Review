		boolean interrupted = false;
		try {
			FileSnapshot snapshot = FileSnapshot.save(finalPack);
			if (waitToPreventRacyPack) {
				snapshot.waitUntilNotRacy();
			}
		} catch (InterruptedException e) {
			interrupted = true;
		}
