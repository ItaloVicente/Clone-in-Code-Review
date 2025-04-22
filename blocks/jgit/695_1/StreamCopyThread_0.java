
					for (;;) {
						try {
							dst.write(buf
						} catch (InterruptedIOException wakey) {
							if (flushCounter.get() > 0)
								continue;
							else
								throw wakey;
						}
						break;
					}
