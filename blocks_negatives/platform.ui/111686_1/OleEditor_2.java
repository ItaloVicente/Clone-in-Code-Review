        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {
            @Override
			public void run() {
                clientSite.exec(OLE.OLECMDID_PRINT,
                        OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
            }
        });
