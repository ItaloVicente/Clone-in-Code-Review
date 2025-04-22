			if (c1.isTeamPrivateMember()) {
			    if (GitTraceLocation.CORE.isActive()) {
				GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), "notTeamPrivate " + c1); //$NON-NLS-1$
			    }
			    c1.setTeamPrivateMember(false);
			}
		    }
