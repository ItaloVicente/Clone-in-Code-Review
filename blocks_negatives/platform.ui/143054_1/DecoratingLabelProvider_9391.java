            IDelayedLabelDecorator delayedDecorator = (IDelayedLabelDecorator) currentDecorator;
            if (!delayedDecorator.prepareDecoration(element, oldText)) {
                decorationReady = false;
            }
        }
        settings.setHasPendingDecorations(!decorationReady);

        if (provider instanceof ITreePathLabelProvider) {
