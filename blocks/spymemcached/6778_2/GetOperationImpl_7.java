			try {
				GetsOperation.Callback cb=(GetsOperation.Callback)getCallback();
				cb.gotData(key, flags, responseCas, data);
			} catch (ClassCastException e1) {
				byte[] value = new byte[data.length-key.length()];
				System.arraycopy(data, key.length(), value, 0, data.length-key.length());
				GetlOperation.Callback cb=(GetlOperation.Callback)getCallback();
				cb.gotData(key, flags, value);
			}
