			for (int k = 0; k < c.length; k++) {
				if (c[k] instanceof IContainer) {
					unmarkTeamPrivate((IContainer) c[k]);
				}
				if (c[k].isTeamPrivateMember()) {
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.CORE.getLocation(),
					c[k].setTeamPrivateMember(false);
				}
