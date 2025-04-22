        ms.states().subscribe(new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState lifecycleState) {
                assertNotEquals(LifecycleState.DISCONNECTED, lifecycleState);
                assertNotEquals(LifecycleState.IDLE, lifecycleState);
            }
        });

