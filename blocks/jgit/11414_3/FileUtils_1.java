			try {
				if (!dst.delete())
					delete(dst
				if (src.renameTo(dst))
					return;
			} catch (IOException e) {
			}
