        Iterator iterator = listeners.iterator();
        while (iterator.hasNext()) {
            LabelProviderChangedEvent event = new LabelProviderChangedEvent(
                    this, element);
            ((ILabelProviderListener) iterator.next())
                    .labelProviderChanged(event);
        }
