			try {
				if (dst.isDirectory())
					delete(dst
				else
					delete(dst
				if (src.renameTo(dst))
					return;
			} catch (IOException e) {
			}
