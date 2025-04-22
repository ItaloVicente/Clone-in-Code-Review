					List<RefSpec> specs = new ArrayList<RefSpec>(3);
					for (RemoteRefUpdate update : specification
							.getRefUpdates(uri)) {
						RefSpec spec = new RefSpec();
						spec = spec.setSourceDestination(update.getSrcRef(),
								update.getRemoteName());
						spec = spec.setForceUpdate(update.isForceUpdate());
						specs.add(spec);
					}
