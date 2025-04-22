        context.addFrameworkListener(listener);
        packageAdmin.refreshPackages(bundles);
        synchronized (flag) {
            while (!flag[0]) {
                try {
                    flag.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        context.removeFrameworkListener(listener);
        context.ungetService(packageAdminRef);
    }
