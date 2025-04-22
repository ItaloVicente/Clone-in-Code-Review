			if (memoryBean.getObjectPendingFinalizationCount() == 0) {
				break;
			}
			if (System.currentTimeMillis() > stop) {
				System.out.println(
						"Garbage collection timed out; not all objects collected.");
				break;
			}
		}
