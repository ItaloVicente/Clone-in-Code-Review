						if (close) {
							dst.close();
							break;
						}
					} catch (IOException e) {
						err = e;
						break;
