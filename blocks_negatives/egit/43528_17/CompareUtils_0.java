		 * Note : it would be better to use a remote context here in order to
		 * give the model provider a chance to resolve the remote logical model
		 * instead of only relying on the local one. However, this might be a
		 * long operation and would not really provide more context : we're
		 * trying to determine if the local file can be compared alone, this can
		 * be done by relying on the local model only.
