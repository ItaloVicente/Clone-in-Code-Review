		return new StatusManager.INotificationListener(){
			@Override
			public void statusManagerNotified(int type, StatusAdapter[] adapters) {
				if(type == INotificationTypes.HANDLED){
					FinishedJobs.getInstance().removeErrorJobs();
					StatusAdapterHelper.getInstance().clear();
				}
