			testSaveAll(
					returnValues,
					confirm,
					beforeDirty,
					afterDirty(returnValues, confirm, beforeDirty,
							throwException),
					isSuccessful(returnValues, confirm, beforeDirty,
							throwException),
					saveCalled(returnValues, confirm, beforeDirty,
							throwException), throwException);
