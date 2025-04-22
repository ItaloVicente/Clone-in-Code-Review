											doActivateAdditionalRefs,
											textForTag, textForBranch, monitor);
								} catch (RuntimeException e) {
									throw e;
								} catch (Exception e) {
									throw new InvocationTargetException(e);
								} finally {
									monitor.done();
								}
