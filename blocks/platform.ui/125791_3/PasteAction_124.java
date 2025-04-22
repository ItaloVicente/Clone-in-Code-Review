        shell.getDisplay().syncExec(new Runnable() {
            @Override
			public void run() {
                ResourceTransfer resTransfer = ResourceTransfer.getInstance();
				clipboardData[0] = (IResource[]) clipboard.getContents(resTransfer);
            }
        });
