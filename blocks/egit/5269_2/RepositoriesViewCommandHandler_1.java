				else if (object instanceof FileNode)
					path = ((FileNode) object).getObject()
							.getAbsolutePath();
				else {
					setBaseEnabled(false);
					return;
