				else
					git.branchCreate().setName(name).setStartPoint(commit)
							.setUpstreamMode(SetupUpstreamMode.NOTRACK)
							.call();
			} catch (Exception e) {
				throw new CoreException(Activator.error(e.getMessage(), e));
			}
