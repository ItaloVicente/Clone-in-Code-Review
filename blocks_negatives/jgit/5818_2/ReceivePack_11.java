				unlockPack();
				timeoutIn = null;
				rawIn = null;
				rawOut = null;
				msgOut = null;
				pckIn = null;
				pckOut = null;
				refs = null;
				enabledCapabilities = null;
				commands = null;
				if (timer != null) {
					try {
						timer.terminate();
					} finally {
						timer = null;
					}
				}
