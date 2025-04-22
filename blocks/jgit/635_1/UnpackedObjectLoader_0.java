						int uncompressed = inflater.inflate(hdr
								hdr.length - avail);
						if (uncompressed == 0) {
							throw new CorruptObjectException(id
									"bad stream
						}
						avail += uncompressed;
