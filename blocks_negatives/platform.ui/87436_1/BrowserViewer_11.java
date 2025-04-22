   		 @Override
		public void run() {
   			 while (fileListenerThread != null) {
   				 try {
   					 Thread.sleep(2000);
   				 } catch (Exception e) {
   				 }
   				 synchronized (syncObject) {
						 if (file != null && file.lastModified() != timestamp) {
