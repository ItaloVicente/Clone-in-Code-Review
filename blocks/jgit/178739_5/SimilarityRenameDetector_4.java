					ObjectLoader loader = reader.open(NEW
					if (skipBinaryFiles && SimilarityIndex.isBinary(loader)) {
						pm.update(1);
						continue;
					}
					d = hash(loader);
