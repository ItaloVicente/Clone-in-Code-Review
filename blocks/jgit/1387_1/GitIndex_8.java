				ObjectInserter oi = db.newObjectInserter();
				try {
					InputStream in = new FileInputStream(f);
					try {
						ObjectId newsha1 = oi.insert(Constants.OBJ_BLOB
								.length()
						oi.flush();
						if (!newsha1.equals(sha1))
							modified = true;
						sha1 = newsha1;
					} finally {
						in.close();
					}
				} finally {
					oi.release();
				}
