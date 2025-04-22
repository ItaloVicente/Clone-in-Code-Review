        asyncExec(new Runnable() {
            @Override
			public void run() {
                lockListener.doPendingWork();
            }
        });
