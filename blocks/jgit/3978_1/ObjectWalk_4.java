				int mode = parseMode(buf
				int flags;
				switch (mode >>> TYPE_SHIFT) {
				case TYPE_FILE:
				case TYPE_SYMLINK:
					if (obj == null) {
						obj = new RevBlob(idBuffer);
						obj.flags = SEEN;
						objects.add(obj);
						return obj;
					}
					if (!(obj instanceof RevBlob))
						throw new IncorrectObjectTypeException(obj
					obj.flags = flags = obj.flags | SEEN;
					if ((flags & UNINTERESTING) == 0)
						return obj;
					if (boundary)
						return obj;
					continue;
