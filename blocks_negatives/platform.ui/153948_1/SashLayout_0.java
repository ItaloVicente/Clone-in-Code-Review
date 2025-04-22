					try {
						host.setRedraw(false);
						host.layout();
					} finally {
						host.setRedraw(true);
					}
					host.update();
				} finally {
					layoutUpdateInProgress = false;
			}
