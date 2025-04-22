			for (;;) {
				RevObject obj;
				try {
					obj = q.next();
				} catch (MissingObjectException notFound) {
					continue;
				}
				if (obj == null)
					break;

				last = obj;
				if (obj.has(PEER_HAS))
					continue;

				obj.add(PEER_HAS);
				if (obj instanceof RevCommit)
					((RevCommit) obj).carry(PEER_HAS);
				addCommonBase(obj);

				switch (multiAck) {
				case OFF:
					if (commonBase.size() == 1)
						pckOut.writeString("ACK " + obj.name() + "\n");
					break;
				case CONTINUE:
					pckOut.writeString("ACK " + obj.name() + " continue\n");
					break;
				case DETAILED:
					pckOut.writeString("ACK " + obj.name() + " common\n");
					break;
				}
			}
		} finally {
			q.release();
