
			case Constants.OBJ_REF_DELTA: {
				readFully(pos + p
				long ofs = findDeltaBase(ObjectId.fromRaw(ib));
				return loadDelta(pos + p + 20
			}

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
		} catch (DataFormatException dfe) {
			CorruptObjectException coe = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							getPackFile()));
			coe.initCause(dfe);
			throw coe;
