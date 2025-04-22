            switch (i) {
                case 0:
                    return AdvertiseRefsHook.DEFAULT;
                case 1:
                    return newHooks[0];
                default:
                    return new AdvertiseRefsHookChain(newHooks
            }
