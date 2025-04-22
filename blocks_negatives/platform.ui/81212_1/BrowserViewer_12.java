	   					 timestamp = file.lastModified();
	   					 Display.getDefault().syncExec(new Runnable() {
	 							@Override
								public void run() {
	 								refresh();
	 							}
	   					 });
