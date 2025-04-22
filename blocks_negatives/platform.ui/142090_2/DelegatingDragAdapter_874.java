                if (currentListener != null) {
                    currentListener.dragFinished(event);
                } else {
                    event.doit = false;
                    Iterator<TransferDragSourceListener> iterator = activeListeners.iterator();
                    while (iterator.hasNext()) {
