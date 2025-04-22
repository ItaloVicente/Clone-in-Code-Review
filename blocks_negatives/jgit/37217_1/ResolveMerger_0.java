			if (entry == null)
				continue;
			FileOutputStream fos = new FileOutputStream(new File(
					db.getWorkTree(), mpath));
			try {
				reader.open(entry.getObjectId()).copyTo(fos);
			} finally {
				fos.close();
			}
