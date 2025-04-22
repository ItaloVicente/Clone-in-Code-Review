		StatusManager.getManager().addListener(new StatusManager.INotificationListener(){
					@Override
					public void statusManagerNotified(int type,
							StatusAdapter[] adapters) {
						if (type == INotificationTypes.HANDLED) {
							called[0] = true;
						}
					}
