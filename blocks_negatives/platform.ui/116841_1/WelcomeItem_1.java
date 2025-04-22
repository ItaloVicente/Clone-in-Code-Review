            action = (IAction) actionClass.newInstance();
        } catch (InstantiationException e) {
            logActionLinkError(pluginId, className);
            return;
        } catch (IllegalAccessException e) {
            logActionLinkError(pluginId, className);
            return;
        } catch (ClassCastException e) {
