		System.out.println(Thread.currentThread().getName() + ":\t" +
				"BasePackFetchConnection.negotiate.negotiateBegin - end");

		if (depth > 0 && depth < Transport.DEPTH_INFINITE) {
			handleShallowUnshallowLines();
		}

		System.out
				.println(Thread.currentThread().getName() + ":\t"
						+ "BasePackFetchConnection.negotiate.sendHaves - begin");

