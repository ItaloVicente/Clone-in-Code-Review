											} catch (IOException e) {
												if (GitTraceLocation.CORE.isActive())
													GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
												throw new CoreException(
														Activator
																.error(
																		CoreText.UpdateOperation_failed,
																		e));
											}
