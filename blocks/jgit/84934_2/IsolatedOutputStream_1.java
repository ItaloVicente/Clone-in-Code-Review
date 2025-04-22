					if (pending) {
						try {
							copy();
						} catch (IOException e) {
							err = e;
						} finally {
							pending = false;
							writeDone.signal();
