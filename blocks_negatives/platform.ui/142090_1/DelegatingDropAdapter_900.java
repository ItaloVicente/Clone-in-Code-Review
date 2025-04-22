                    currentListener.dragLeave(event);
                }
            });
        }
        currentListener = listener;
        if (currentListener != null) {
        	SafeRunnable.run(new SafeRunnable() {
                @Override
