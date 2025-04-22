			if (w == null || w.length() == 0)
				return null;
			File parentFile = new File(w).getParentFile();
			if (parentFile == null)
				return null;
			return parentFile.getParentFile();
