		String messageText = NLS.bind(UIText.CloneFailureDialog_checkList,
				uri.toString());
		int bullets = 2;
		if (!uri.getPath().endsWith(".git")) { //$NON-NLS-1$
			messageText = messageText + UIText.CloneFailureDialog_checkList_git;
			bullets += 1;
		}
		if ("ssh".equals(uri.getScheme())) { //$NON-NLS-1$
			messageText = messageText + UIText.CloneFailureDialog_checkList_ssh;
			bullets += 1;
		} else if ("https".equals(uri.getScheme())) { //$NON-NLS-1$
			messageText = messageText
					+ UIText.CloneFailureDialog_checkList_https;
			bullets += 1;
		}
