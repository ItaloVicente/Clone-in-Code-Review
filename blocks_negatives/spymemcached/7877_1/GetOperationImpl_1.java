		OperationCallback cb = getCallback();
		if (cb instanceof GetOperation.Callback) {
			GetOperation.Callback gcb=(GetOperation.Callback)cb;
			gcb.gotData(key, flags, data);
		} else if (cb instanceof GetsOperation.Callback) {
			GetsOperation.Callback gcb=(GetsOperation.Callback)cb;
			gcb.gotData(key, flags, responseCas, data);
		} else if (cb instanceof GetlOperation.Callback) {
			GetlOperation.Callback gcb=(GetlOperation.Callback)cb;
			gcb.gotData(key, flags, responseCas, data);
		} else if (cb instanceof GetAndTouchOperation.Callback) {
			GetAndTouchOperation.Callback gcb=(GetAndTouchOperation.Callback)cb;
			gcb.gotData(key, flags, responseCas, data);
		} else {
			throw new ClassCastException("Couldn't convert " + cb + "to a relevent op");
		}
