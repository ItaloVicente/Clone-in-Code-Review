		try {
			GetOperation.Callback cb=(GetOperation.Callback)getCallback();
			cb.gotData(key, flags, data);
		} catch(ClassCastException e) {
			GetsOperation.Callback cb=(GetsOperation.Callback)getCallback();
			cb.gotData(key, flags, responseCas, data);
