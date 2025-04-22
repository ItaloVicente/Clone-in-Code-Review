					((SideBandOutputStream) msgOut).flushBuffer();
					((SideBandOutputStream) rawOut).flushBuffer();

					PacketLineOut plo = new PacketLineOut(output);
					plo.setFlushOnEnd(false);
					plo.end();
				}

				if (biDirectionalPipe) {
					if (!sideBand && msgOut != null)
						msgOut.flush();
					rawOut.flush();
