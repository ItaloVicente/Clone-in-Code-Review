				}
			});
			return true;
		} catch (Throwable e) {
			if (e instanceof InvocationTargetException) {
				e = e.getCause();
