		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();
		assertEquals(1
		assertEquals(1
		assertFalse(runnable1.isThrown());
		assertFalse(runnable2.isThrown());
