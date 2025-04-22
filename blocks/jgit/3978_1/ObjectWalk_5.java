				case TYPE_TREE:
					if (obj == null) {
						obj = new RevTree(idBuffer);
						obj.flags = SEEN;
						objects.add(obj);
						return enterTree(obj);
					}
					if (!(obj instanceof RevTree))
						throw new IncorrectObjectTypeException(obj
					obj.flags = flags = obj.flags | SEEN;
					if ((flags & UNINTERESTING) == 0)
						return enterTree(obj);
					if (boundary)
						return enterTree(obj);
					continue;

				case TYPE_GITLINK:
					continue;

				default:
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().corruptObjectInvalidMode3
							String.format("%o"
							RawParseUtils.decode(buf
							tv.obj));
				}
			}

			currVisit = tv.parent;
			releaseTreeVisit(tv);
			tv = currVisit;
