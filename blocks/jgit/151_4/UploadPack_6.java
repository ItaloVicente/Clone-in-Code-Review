						break;
					case DETAILED:
						pckOut.writeString("ACK " + id.name() + " common\n");
						break;
					}
				} else if (okToGiveUp()) {
