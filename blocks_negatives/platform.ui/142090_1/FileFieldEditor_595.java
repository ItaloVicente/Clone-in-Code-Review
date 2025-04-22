            } else {
                msg = getErrorMessage();
            }
        }

            showErrorMessage(msg);
            return false;
        }

	        clearErrorMessage();
	        return true;
        }
        if (msg != null) {
            showErrorMessage(msg);
        }
    	return false;
    }

    /**
     * Helper to open the file chooser dialog.
     * @param startingDirectory the directory to open the dialog on.
     * @return File The File the user selected or <code>null</code> if they
     * do not.
     */
    private File getFile(File startingDirectory) {

        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.SHEET);
        if (startingDirectory != null) {
