
			ObjectReader reader = db.newObjectReader();
			try {
				for (final Entry<String
					final Ref ref = e.getValue();
					printHead(reader
							current.equals(ref.getName())
				}
			} finally {
				reader.release();
