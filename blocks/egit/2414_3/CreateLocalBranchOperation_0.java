					if (ref != null) {
						SetupUpstreamMode mode;
						if (upstreamConfig == UpstreamConfig.NONE)
							mode = SetupUpstreamMode.NOTRACK;
						else
							mode = SetupUpstreamMode.SET_UPSTREAM;
