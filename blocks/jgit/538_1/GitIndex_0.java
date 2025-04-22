			long length = file.length();
			if (length == 0) {
				if (!file.exists())
					return true;
			}
			if (length != size)
