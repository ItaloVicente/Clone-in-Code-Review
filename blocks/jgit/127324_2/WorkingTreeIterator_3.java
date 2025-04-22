				OperationType operationType = opType != null ? opType
						: state.walk.getOperationType();
				if (OperationType.CHECKIN_OP.equals(operationType)
						&& EolStreamType.AUTO_LF.equals(type)
						&& has_crlf_in_index(getDirCacheIterator())) {
					type = EolStreamType.DIRECT;
				}
