	public void addCallbacks(GetOperation o) {
		GetOperation.Callback c=new GetCallbackWrapper(o.getKeys().size(),
				(GetOperation.Callback)o.getCallback());
		allCallbacks.add(c);
		for(String s : o.getKeys()) {
			Collection<GetOperation.Callback> cbs=callbacks.get(s);
			if(cbs == null) {
				cbs=new ArrayList<GetOperation.Callback>();
				callbacks.put(s, cbs);
			}
			cbs.add(c);
		}
	}
