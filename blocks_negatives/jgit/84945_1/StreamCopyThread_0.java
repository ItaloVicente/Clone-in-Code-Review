						for (;;) {
							try {
								dst.write(buf, 0, n);
							} catch (InterruptedIOException wakey) {
								writeInterrupted = true;
								continue;
							}
