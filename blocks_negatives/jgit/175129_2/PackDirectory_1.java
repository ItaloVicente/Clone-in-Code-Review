			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension())) {
					extensions |= ext.getBit();
				}
			}
