		OperationCallback cb = getCallback();
		if (cb instanceof GetOperation.Callback) {
			GetOperation.Callback gcb=(GetOperation.Callback)cb;
			gcb.gotData(key, flags, data);
		} else if (cb instanceof GetsOperation.Callback) {
			GetsOperation.Callback gcb=(GetsOperation.Callback)cb;
			gcb.gotData(key, flags, responseCas, data);
		} else if (cb instanceof GetlOperation.Callback) {
			byte[] value = new byte[data.length-key.length()];
			System.arraycopy(data, key.length(), value, 0, data.length-key.length());
			GetlOperation.Callback gcb=(GetlOperation.Callback)cb;
			gcb.gotData(key, flags, responseCas, value);
		} else {
			throw new ClassCastException("Couldn't convert " + cb + "to a relevent op");
