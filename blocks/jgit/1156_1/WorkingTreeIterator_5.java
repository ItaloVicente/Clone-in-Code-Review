			if (Constants.DOT_GIT_IGNORE.equals(name)) {
				InputStream in = e.openInputStream();
				try {
					ignoreNode = new IgnoreNode();
					ignoreNode.parse(in);
				} finally {
					in.close();
				}
			}
