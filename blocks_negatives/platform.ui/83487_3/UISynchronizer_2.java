        try {
            do {
                if (lockListener.isUIWaiting()) {
					lockListener.interruptUI(runnable);
				}
            } while (!work.acquire(1000));
        } catch (InterruptedException e) {
        }
    }
