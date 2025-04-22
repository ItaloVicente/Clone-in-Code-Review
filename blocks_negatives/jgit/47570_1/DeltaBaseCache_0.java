				Entry buf = e.data.get();
				if (buf != null) {
					moveToHead(e);
					return buf;
				}
				return null;
