				if (!asText && !asBinary && ent.getChangeType() != RENAME) {
					buf.write(encodeASCII(
							String.format("Binary files " +
									"a/%s and b/%s differ\n"
									ent.getOldPath()
									ent.getNewPath()

					editList = new EditList();
					type = PatchType.BINARY;

					res.header = new FileHeader(buf.toByteArray()
							editList
					return res;
				}
				try {
					aRaw = openBinary(OLD
					bRaw = openBinary(NEW
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				if (ent.getChangeType() == ADD) {
					if (asBinary) {
						assert bRaw != null;


						writeLiteralPatch(buf

						buf.write('\n');

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					} else if (asText) {
						formatOldNewPaths(buf

						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					}
				} else if (ent.getChangeType() == MODIFY) {
					if (asBinary) {
						assert aRaw != null;
						assert bRaw != null;


						writeDeltaPatch(buf
						writeDeltaPatch(buf

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					} else if (asText) {

						formatOldNewPaths(buf

						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					}
				} if (ent.getChangeType() == DELETE) {
					if (asBinary) {
						assert aRaw != null;

						buf.write('\n');

						writeLiteralPatch(buf

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					} else if (asText) {
						formatOldNewPaths(buf

						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					}
				} else {
					editList = new EditList();
					type = PatchType.BINARY;

					res.header = new FileHeader(buf.toByteArray()
							editList
					return res;
				}
