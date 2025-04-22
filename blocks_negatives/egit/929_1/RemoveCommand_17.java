				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						getView(event).getCommonViewer().refresh();
					}
				});

