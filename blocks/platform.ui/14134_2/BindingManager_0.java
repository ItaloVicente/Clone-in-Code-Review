				
				if (!goToNextBinding) {
					contextPointer = currentContext;
					String contextPointer2 = bestContext;
					String lastContextPointer = contextPointer, lastContextPointer2 = contextPointer2;
					int currentContextLength = 0, bestContextLength = 0;
					
					while (contextPointer != null || contextPointer2 != null) {
						contextPointer = (String) activeContextTree.get(contextPointer);
						if (contextPointer != null) {
							lastContextPointer = contextPointer;
							currentContextLength++;
						}
						contextPointer2 = (String) activeContextTree.get(contextPointer2);
						if (contextPointer2 != null) {
							lastContextPointer2 = contextPointer2;
							bestContextLength++;
						}						
					}
					
					if (lastContextPointer.equals(lastContextPointer2)) {
						if (currentContextLength > bestContextLength) {
							bestMatch = current;
							conflict = false;
							goToNextBinding = true;
						} else if (bestContextLength > currentContextLength) {
							goToNextBinding = true;
						}
					}					
				}
