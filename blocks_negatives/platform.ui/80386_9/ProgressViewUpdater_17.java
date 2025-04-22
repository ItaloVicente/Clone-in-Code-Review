
				return Status.OK_STATUS;
			}

			@Override
			protected void canceling() {
				synchronized (updateScheduled) {
					updateScheduled.value = false;
