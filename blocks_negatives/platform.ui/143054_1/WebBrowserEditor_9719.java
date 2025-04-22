            }
        }

        try {
            URL theURL = new URL(webBrowser.getURL());
            IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
            support.getExternalBrowser().openURL(theURL);
        }
        catch (MalformedURLException e) {
        }
        catch (PartInitException e) {
        }
    }
