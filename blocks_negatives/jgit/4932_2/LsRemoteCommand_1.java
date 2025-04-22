					else
						for (Ref r : refs)
							for (RefSpec rs : refSpecs)
								if (rs.matchSource(r)) {
									refmap.put(r.getName(), r);
									break;
								}
				} finally {
					fc.close();
				}
				return refmap.values();

			} catch (TransportException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfLsRemoteCommand,
						e);
			} finally {
				transport.close();
			}
