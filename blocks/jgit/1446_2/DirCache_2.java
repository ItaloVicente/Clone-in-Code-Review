			case EXT_RESOLVE_UNDO: {
				if (sz != 0) {
					throw new IOException(
							"The Index contains resolve-undo entries. These can't be processed currently.");
				}
				break;
			}
