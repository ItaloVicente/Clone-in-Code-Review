				try {
					GetsOperation.Callback gcb=(GetsOperation.Callback)
					getCallback();
					gcb.gotData(currentKey, currentFlags, casValue, data);
				} catch (ClassCastException e1) {
					GetlOperation.Callback gcb=(GetlOperation.Callback)
