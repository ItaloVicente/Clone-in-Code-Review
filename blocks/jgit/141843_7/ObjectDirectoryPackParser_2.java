		boolean interrupted = false;
		try {
			FileSnapshot snapshot = FileSnapshot.save(finalPack);
			snapshot.waitUntilNotRacy();
		} catch (InterruptedException e) {
			interrupted = true;
		}
