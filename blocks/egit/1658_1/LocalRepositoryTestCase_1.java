		boolean deleted = false;
		for(int i=0; i<10; i++) {
			deleted = dirOrFile.delete();
			if (deleted)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
