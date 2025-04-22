				while ((c & 128) != 0)
					c = readFrom(Source.FILE) & 0xff;
				visit.data = BinaryDelta.apply(visit.parent.data
				break;
			}
			case Constants.OBJ_REF_DELTA: {
				crc.update(buf
				use(20);
				visit.data = BinaryDelta.apply(visit.parent.data
				break;
			}
			default:
				throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType
			}
