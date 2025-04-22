		log(msg, t);
	}

	public static void log(String message, IStatus status) {


		if (message != null) {
			getDefault().getLog().log(
					StatusUtil.newStatus(IStatus.ERROR, message, null));
		}

		getDefault().getLog().log(status);
	}

	@Override
