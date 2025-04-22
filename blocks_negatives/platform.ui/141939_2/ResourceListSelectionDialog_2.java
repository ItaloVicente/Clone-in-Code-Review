                }
            } else {
                last = lastMatch;
                boolean setFirstMatch = true;
                for (int i = firstMatch; i <= lastMatch; i++) {
                    if (i % 50 == 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }
                    if (stop || resourceNames.isDisposed()) {
                        disposed[0] = true;
                        return;
                    }
                    final int index = i;
                    if (match(descriptors[index].label)) {
                        if (setFirstMatch) {
                            setFirstMatch = false;
                            firstMatch = index;
                        }
                        last = index;
                        display.syncExec(() -> {
						    if (stop || resourceNames.isDisposed()) {
