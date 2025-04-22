
package org.eclipse.core.tests.internal.databinding.beans;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBean {
	public String getValue();

	public void setValue(String value);

	public Object[] getArray();

	public void setArray(Object[] array);

	public List getList();

	public void setList(List list);

	public Set getSet();

	public void setSet(Set set);

	public Map getMap();

	public void setMap(Map map);
}
