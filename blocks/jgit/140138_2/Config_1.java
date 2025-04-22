				case '\r': {
					int next = in.read();
					if (next == '\n') {
					} else if (next >= 0) {
						in.reset();
					}
					break;
				}
