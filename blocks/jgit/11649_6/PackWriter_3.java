			ObjectToPack b = otp.getDeltaBase();
				otp.setVisited();
				int d = 0;
				do {
					if (d < b.getChainLength())
						break;
					b.setChainLength(++d);
						reselectNonDelta(b);
						break;
					}
					b.setVisited();
					b = b.getDeltaBase();
				} while (b != null);
				for (b = otp; b != null && b.visited(); b = b.getDeltaBase())
					b.clearVisited();
				continue;
			}
