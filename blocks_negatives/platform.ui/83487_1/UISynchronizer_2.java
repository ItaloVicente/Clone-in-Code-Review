        try {
            do {
                if (lockListener.isUIWaiting()) {
					lockListener.interruptUI();
				}
            } while (!work.acquire(1000));
        } catch (InterruptedException e) {
        }
    }
