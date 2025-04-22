			if (Util.isCocoa()
					&& stackTraces.length > 1) {
				element = stackTraces[1];
				methodName = element.getMethodName();
				className = element.getClassName();
			}
