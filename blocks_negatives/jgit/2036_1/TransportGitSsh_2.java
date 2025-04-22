
			if (channel != null) {
				try {
					exitStatus = channel.getExitStatus();
					if (channel.isConnected())
						channel.disconnect();
				} finally {
					channel = null;
				}
			}
