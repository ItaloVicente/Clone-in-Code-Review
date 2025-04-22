		} else {
			try {
				return gcTask.call();
			} catch (Exception e) {
				throw new IOException(e);
			}
