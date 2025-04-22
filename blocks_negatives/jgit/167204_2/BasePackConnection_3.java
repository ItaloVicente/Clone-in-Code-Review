				throw eof;
			}
			if (PacketLineIn.isEnd(line))
				break;

				throw new RemoteRepositoryException(uri, line.substring(4));
			}

			if (avail.isEmpty()) {
