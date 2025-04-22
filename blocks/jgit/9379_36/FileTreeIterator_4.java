			if (length < 0) {
				try {
					length = fs.length(file);
				} catch (IOException e) {
					length = 0;
				}
			}
