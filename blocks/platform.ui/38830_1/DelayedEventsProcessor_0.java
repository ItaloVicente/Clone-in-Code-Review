				int line = -1;
				int col = -1;
				if (fetchInfo.isDirectory() || !fetchInfo.exists()) {
					int plusIndex = path.lastIndexOf('+');
					if (plusIndex >= 0) {
						String lineCol = path.substring(plusIndex + 1);
						path = path.substring(0, plusIndex);

						int commaIndex = lineCol.indexOf(':');
						if (commaIndex != -1) {
							String lineStr = lineCol.substring(0, commaIndex);
							String colStr = lineCol.substring(commaIndex + 1, lineCol.length());
							try {
								line = Integer.parseInt(lineStr);
							} catch (NumberFormatException e1) {
							}
							try {
								col = Integer.parseInt(colStr);
							} catch (NumberFormatException e) {
							}
						} else {
							try {
								line = Integer.parseInt(lineCol);
							} catch (NumberFormatException e) {
							}
						}

						fileStore = EFS.getLocalFileSystem().getStore(new Path(path));
						fetchInfo = fileStore.fetchInfo();
					}
				}

