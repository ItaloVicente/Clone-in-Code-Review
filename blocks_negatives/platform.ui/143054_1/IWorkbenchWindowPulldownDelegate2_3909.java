 * Extension of IWorkbenchWindowPulldownDelegate that allows the delegate dropdown
 * menu to be a child of a Menu item.  Necessary for CoolBar support.  If a coolbar
 * group of items is not fully displayed, a chevron and a drop down menu will be
 * used to show the group's tool items.  Therefore, a getMenu(Menu) method is necessary,
 * since the delegate drop down menu will be a child of the chevron menu item (not
 * the tool control).
