                if (holder[0] != null) {
                    if (holder[0] instanceof InvocationTargetException) {
                        throw (InvocationTargetException) holder[0];
                    } else if (holder[0] instanceof InterruptedException) {
                        throw (InterruptedException) holder[0];
                    }
                }
            } finally {
                operationInProgress = false;
                for (int i = 0; i < shells.length; i++) {
                    Shell current = shells[i];
                    if (current == shell) {
