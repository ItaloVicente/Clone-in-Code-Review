						boolean writeInterrupted = false;
						for (;;) {
							try {
								dst.write(buf
							} catch (InterruptedIOException wakey) {
								writeInterrupted = true;
								continue;
							}

							if (writeInterrupted || flushCount.get() > 0)
								interrupt();
							break;
						}
