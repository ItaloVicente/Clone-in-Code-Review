        BusyIndicator.showWhile(clientSite.getDisplay(), new Runnable() {

            @Override
			public void run() {

                if (!sourceChanged) {
                    int result = clientSite.queryStatus(OLE.OLECMDID_SAVE);
                    if ((result & OLE.OLECMDF_ENABLED) != 0) {
                        result = clientSite.exec(OLE.OLECMDID_SAVE,
                                OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
                        if (result == OLE.S_OK) {
                            try {
                                resource.refreshLocal(IResource.DEPTH_ZERO,
                                        monitor);
                            } catch (CoreException ex) {
                            }
                            return;
                        }
                        displayErrorDialog(OLE_EXCEPTION_TITLE,
                                OLE_EXCEPTION_MESSAGE + String.valueOf(result));
                        return;
                    }
                }
                if (saveFile(source)) {
                    try {
                    	if (resource != null)
                    		resource.refreshLocal(IResource.DEPTH_ZERO, monitor);
                    } catch (CoreException ex) {
                    }
                } else
                    displayErrorDialog(SAVE_ERROR_TITLE, SAVE_ERROR_MESSAGE
                            + source.getName());
            }
        });
