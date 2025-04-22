            if (listener.getTransfer().isSupportedType(event.dataType)) {
                currentListener = listener;
                return;
            }
        }
    }
