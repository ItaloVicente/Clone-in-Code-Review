				final ObjectId id = ObjectId.fromString(line.substring(5));
				if (matchHave(id)) {
					last = id;
					switch (multiAck) {
					case OFF:
						if (commonBase.size() == 1)
							pckOut.writeString("ACK " + id.name() + "\n");
						break;
					case CONTINUE:
						pckOut.writeString("ACK " + id.name() + " continue\n");
						break;
					case DETAILED:
						pckOut.writeString("ACK " + id.name() + " common\n");
						break;
					}
				} else if (okToGiveUp()) {
					switch (multiAck) {
					case OFF:
						break;
					case CONTINUE:
						pckOut.writeString("ACK " + id.name() + " continue\n");
						break;
					case DETAILED:
						pckOut.writeString("ACK " + id.name() + " ready\n");
						break;
					}
				}
