            switch (i) {
                case 0:
                    return PreReceiveHook.NULL;
                case 1:
                    return newHooks[0];
                default:
                    return new PreReceiveHookChain(newHooks
            }
