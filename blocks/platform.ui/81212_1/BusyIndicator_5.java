						Display.getDefault().syncExec(() -> {
							if (!stop) {
								if (count < 13)
									setImage(images[count]);
								count++;
								if (count > 12)
									count = 1;
