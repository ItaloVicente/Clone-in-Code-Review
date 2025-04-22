						ObjectLoader loader = reader.open(OLD
						if (skipBinaryFiles && SimilarityIndex.isBinary(loader)) {
							pm.update(1);
							continue SRC;
						}
						s = hash(loader);
