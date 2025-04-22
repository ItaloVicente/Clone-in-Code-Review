    public void testExistsIn() {
        DocumentFragment<Lookup> resultSub = ctx.bucket().lookupIn(key).exists("sub").doLookup();
        DocumentFragment<Lookup> resultInt = ctx.bucket().lookupIn(key).exists("int").doLookup();
        DocumentFragment<Lookup> resultString = ctx.bucket().lookupIn(key).exists("string").doLookup();
        DocumentFragment<Lookup> resultArray = ctx.bucket().lookupIn(key).exists("array").doLookup();
        DocumentFragment<Lookup> resultBoolean = ctx.bucket().lookupIn(key).exists("boolean").doLookup();
