	private INotificationListener createNotificationListener() {
		return (type, adapters) -> {
			if(type == INotificationTypes.HANDLED){
				FinishedJobs.getInstance().removeErrorJobs();
				StatusAdapterHelper.getInstance().clear();
			}
		};
	}

