
		menuManager.add(new Action(IDEWorkbenchMessages.OpenResourceDialog_copyLocation_label) {
			public void run() {
				if (getSelectedItems().isEmpty()) {
					return;
				}

				StringBuilder str = new StringBuilder();
				Iterator iter = getSelectedItems().iterator();
				while (iter.hasNext()) {
					Object o = iter.next();
					if (o instanceof IFile) {
						IFile f = (IFile)o;
						IPath p = f.getLocation();
						if (p != null) {
							str.append(p.toOSString());
							str.append('\n');
						}
					}
				}

				if (str.length() > 0) {
					Clipboard clipboard = null;
					try {
						clipboard = new Clipboard(getShell().getDisplay());
						clipboard.setContents(new Object[] { str.toString() },
								new Transfer[] { TextTransfer.getInstance() });
					} finally {
						if (clipboard != null) {
							clipboard.dispose();
						}
					}
				}
			}
		});
