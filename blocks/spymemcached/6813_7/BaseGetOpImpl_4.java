			} else if (cb instanceof GetAndTouchOperation.Callback) {
				GetAndTouchOperation.Callback gcb=(GetAndTouchOperation.Callback)cb;
				gcb.gotData(currentKey, currentFlags, casValue, data);
			}else {
				throw new ClassCastException("Couldn't convert " + cb +
						"to a relevent op");
