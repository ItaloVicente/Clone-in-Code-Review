
        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            currentInfo.refresh(info);
        }
