	private TeamException throwTeamException(Throwable th) throws TeamException{
		Throwable current = th;
		while(current.getCause()!=null){
			current = current.getCause();
		}
		throw new TeamException(current.getMessage(), current);
	}

