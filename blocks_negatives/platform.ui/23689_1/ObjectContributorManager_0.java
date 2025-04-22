    protected final List computeInterfaceOrder(List classList) {
        ArrayList result = new ArrayList(4);
        Map seen = new HashMap(4);
        for (Iterator list = classList.iterator(); list.hasNext();) {
            Class[] interfaces = ((Class) list.next()).getInterfaces();
            internalComputeInterfaceOrder(interfaces, result, seen);
