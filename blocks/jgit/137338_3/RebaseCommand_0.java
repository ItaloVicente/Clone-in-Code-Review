			try {
				return readFile(getDir()
			} catch (FileNotFoundException e) {
				if (ONTO_NAME.equals(name)) {
					File oldFile = getFile(ONTO_NAME.replace('_'
					if (oldFile.exists()) {
						return readFile(oldFile);
					}
				}
				throw e;
			}
