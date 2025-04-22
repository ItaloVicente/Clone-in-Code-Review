				if (isCrLf) {
					out.write('\r');
				}
				out.write('\n');
				lastw = i + 1;
				buf = -1;
