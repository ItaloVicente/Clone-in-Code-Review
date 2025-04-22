                RegistryTriggerPoint triggerPoint = new RegistryTriggerPoint(
                        id, element);
                triggerMap.put(id, triggerPoint);
                tracker.registerObject(extension, triggerPoint,
                        IExtensionTracker.REF_WEAK);
            }
        }
    }
