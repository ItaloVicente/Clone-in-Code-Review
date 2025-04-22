						}
					}
				}
			}
		};
		fileListenerThread.setDaemon(true);
		fileListenerThread.setPriority(Thread.MIN_PRIORITY);

		locationListener2 = new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				File temp = getFile(event.location);
				if (temp != null && temp.exists()) {
					synchronized (syncObject) {
						file = temp;
						timestamp = file.lastModified();
					}
				} else
					file = null;
			}
		};
		browser.addLocationListener(locationListener2);

		File temp = getFile(browser.getUrl());
		if (temp != null && temp.exists()) {
			file = temp;
			timestamp = file.lastModified();
		}
		fileListenerThread.start();
