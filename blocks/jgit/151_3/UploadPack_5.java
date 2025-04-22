					last = id;
					switch (multiAck) {
					case OFF:
						if (commonBase.size() == 1)
							pckOut.writeString("ACK " + id.name() + "\n");
						break;
					case CONTINUE:
