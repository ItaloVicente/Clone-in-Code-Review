        getDisplay().syncExec(() -> {
		    MessageDialog dialog = new MessageDialog(
		            getShell(),
		            ResourceNavigatorMessages.DropAdapter_question, null, msg, MessageDialog.QUESTION, options, 0);
		    dialog.open();
		    int returnVal = dialog.getReturnCode();
		    String[] returnCodes = { YES, ALL, NO, CANCEL };
		    returnCode[0] = returnVal < 0 ? CANCEL : returnCodes[returnVal];
		});
