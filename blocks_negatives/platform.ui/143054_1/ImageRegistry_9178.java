            if (device == originalDisplay) {
                refCount++;
                return original;
            }
            return super.createResource(device);
        }

        @Override
