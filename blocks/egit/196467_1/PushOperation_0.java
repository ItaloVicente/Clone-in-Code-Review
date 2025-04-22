						try (ByteArrayOutputStream hookOutBytes = new ByteArrayOutputStream();
								ByteArrayOutputStream hookErrBytes = new ByteArrayOutputStream();
								PrintStream stdout = new PrintStream(hookOutBytes, true, hookCharset);
								PrintStream stderr = new PrintStream(hookErrBytes, true, hookCharset)) {
							transport.setHookOutputStream(stdout);
							transport.setHookErrorStream(stderr);
							PushResult result = transport.push(gitSubMonitor,
									refUpdates, out);
							stdout.flush();
							stderr.flush();
							addHookMessage(result.getURI(),
									hookOutBytes.toString(hookCharset),
									allHookOutputs);
							addHookMessage(result.getURI(),
									hookErrBytes.toString(hookCharset),
									allHookErrors);
							operationResult.addOperationResult(result.getURI(),
									result);
							specification.addURIRefUpdates(result.getURI(),
									result.getRemoteUpdates());
						}
