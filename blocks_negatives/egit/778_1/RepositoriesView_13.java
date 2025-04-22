			@Override
			public void run() {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				if (sel.size() == 1) {
					RepositoryTreeNode node = (RepositoryTreeNode) sel
							.getFirstElement();
					String dir = null;
					if (node.getType() == RepositoryTreeNodeType.REPO) {
						dir = node.getRepository().getDirectory().getPath();
					} else if (node.getType() == RepositoryTreeNodeType.FILE
							|| node.getType() == RepositoryTreeNodeType.FOLDER) {
						dir = ((File) node.getObject()).getPath();
					} else if (node.getType() == RepositoryTreeNodeType.WORKINGDIR) {
						if (!isBare(node.getRepository()))
							dir = node.getRepository().getWorkDir().getPath();
					}
					if (dir != null) {
						Clipboard clip = null;
						try {
							clip = new Clipboard(getSite().getShell()
									.getDisplay());
							clip
									.setContents(new Object[] { dir },
											new Transfer[] { TextTransfer
													.getInstance() });
						} finally {
							if (clip != null)
								clip.dispose();
						}
