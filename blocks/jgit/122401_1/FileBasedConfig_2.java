				return;
			} catch (FileNotFoundException noFile) {
				if (configFile.exists()) {
					throw noFile;
				}
				clear();
