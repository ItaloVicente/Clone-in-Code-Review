								boolean enabled = true;
								switch (aMode) {
								case EFFECTIVE:
									enabled = false;
									break;
								case SYSTEM:
									enabled = systemConfig.getFile() != null
											&& systemConfig.getFile()
													.canWrite();
									break;
								default:
								}
								editAction.getAction().setEnabled(enabled);
