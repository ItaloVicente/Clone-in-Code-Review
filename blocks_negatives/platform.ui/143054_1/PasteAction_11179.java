        final IResource[][] clipboardData = new IResource[1][];
        shell.getDisplay().syncExec(new Runnable() {
            @Override
			public void run() {
                ResourceTransfer resTransfer = ResourceTransfer.getInstance();
				clipboardData[0] = (IResource[]) clipboard.getContents(resTransfer);
            }
        });
        IResource[] resourceData = clipboardData[0];
        boolean isProjectRes = resourceData != null && resourceData.length > 0
                && resourceData[0].getType() == IResource.PROJECT;
