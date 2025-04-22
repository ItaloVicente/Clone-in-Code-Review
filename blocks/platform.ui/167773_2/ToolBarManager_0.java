				Point beforePack = layoutBar.getSize();
				layoutBar.pack(true);
				Point afterPack = layoutBar.getSize();

				if (beforePack.equals(afterPack)) {
					return;
				}
