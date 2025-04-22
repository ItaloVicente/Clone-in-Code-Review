					if (timeout(start)) {
						LOG.warn(MessageFormat.format(JGitText
								.get().timeoutMeasureFsTimestampResolution
								s.toString()));
						fsTimestampResolution = FALLBACK_TIMESTAMP_RESOLUTION;
						return;
					}
