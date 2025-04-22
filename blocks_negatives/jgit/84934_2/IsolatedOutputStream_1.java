					try {
						if (len > 0) {
							dst.write(buffer, 0, len);
							len = 0;
						}
						if (flush) {
							dst.flush();
							flush = false;
