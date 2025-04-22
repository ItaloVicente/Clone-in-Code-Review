        BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
            @Override
			public void run() {
                results[0] = createRootElement(rootFileSystemObject,
                        structureProvider);
            }
        });
