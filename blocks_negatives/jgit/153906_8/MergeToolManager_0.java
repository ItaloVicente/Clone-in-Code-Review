			if (ExternalToolUtils.isToolAvailable(db.getFS(), db.getDirectory(),
					db.getWorkTree(), tool.getPath())) {
						name = tool.getName();
						break;
					}
				}
				return name;
