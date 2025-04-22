			boolean interrupted = false;
			try {
				FileSnapshot snapshot = FileSnapshot.save(realPack);
				if (pconfig.isWaitToPreventRacyPack()) {
					snapshot.waitUntilNotRacy();
				}
			} catch (InterruptedException e) {
				interrupted = true;
			}
			try {
				return repo.getObjectDatabase().openPack(realPack);
			} finally {
				if (interrupted) {
					Thread.currentThread().interrupt();
				}
			}
