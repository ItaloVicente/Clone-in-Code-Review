				else
					git.branchCreate().setName(name).setStartPoint(commit)
							.setUpstreamMode(SetupUpstreamMode.NOTRACK)
							.call();
			} catch (Exception e1) {
				throw new CoreException(Activator.error(e1.getMessage(), e1));
			}
