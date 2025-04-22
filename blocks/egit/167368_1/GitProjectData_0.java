
			try {
				FileUtils.rename(tmp, dat, StandardCopyOption.ATOMIC_MOVE,
						StandardCopyOption.REPLACE_EXISTING);
			} catch (AtomicMoveNotSupportedException e) {
				FileUtils.rename(tmp, dat, StandardCopyOption.REPLACE_EXISTING);
