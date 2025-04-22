				if (Platform.getOS().equals(Platform.OS_WIN32)) {
					try {
						host.setRedraw(false);
						host.layout();
					} finally {
						host.setRedraw(true);
					}
					host.update();
				} else {
					host.requestLayout();
