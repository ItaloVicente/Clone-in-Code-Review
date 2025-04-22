				if (removed || canonicalDir.equals(file.getAbsolutePath()))
					return removed;

				return removeDir(file.getAbsolutePath());
			} catch (IOException e) {
				return removeDir(file.getAbsolutePath());
