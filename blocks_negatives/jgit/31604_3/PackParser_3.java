					if (r == 0) {
						if (inf.finished())
							break;
						if (inf.needsInput()) {
							onObjectData(src, buf, p, bAvail);
							use(bAvail);

							p = fill(src, 1);
							inf.setInput(buf, p, bAvail);
						} else {
							throw new CorruptObjectException(
									MessageFormat
											.format(
													JGitText.get().packfileCorruptionDetected,
													JGitText.get().unknownZlibError));
						}
					} else {
						n += r;
