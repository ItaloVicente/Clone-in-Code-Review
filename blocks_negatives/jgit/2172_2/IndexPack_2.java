			data = BinaryDelta.apply(data, inflateAndReturn(Source.FILE, sz));
			break;
		}
		case Constants.OBJ_REF_DELTA: {
			crc.update(buf, fill(Source.FILE, 20), 20);
			use(20);
			data = BinaryDelta.apply(data, inflateAndReturn(Source.FILE, sz));
			break;
		}
		default:
			throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, typeCode));
		}
