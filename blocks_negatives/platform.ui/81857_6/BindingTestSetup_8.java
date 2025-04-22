		org.eclipse.jface.util.Policy.setLog(new org.eclipse.jface.util.ILogger(){
			@Override
			public void log(IStatus status) {
				if (status.getException() != null) {
					throw new RuntimeException(status.getException());
				}
				fail();
			}
		});
