/*
  MTNP: Manipulate Tables N'Plots
  Copyright (C) 2017 Sylvain Hallé

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.mtnp.plot.gral;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.uqac.lif.mtnp.table.HardTable;
import ca.uqac.lif.mtnp.table.Table;
import ca.uqac.lif.mtnp.table.TempTable;
import ca.uqac.lif.petitpoucet.NodeFunction;
import de.erichseifert.gral.data.Column;
import de.erichseifert.gral.data.DataListener;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.Row;
import de.erichseifert.gral.data.statistics.Statistics;

public class GralDataTable extends Table implements DataSource
{
	/**
	 * The data listeners associated to this table
	 */
	protected Set<DataListener> m_dataListeners;
	
	protected HardTable m_table;
	
	private GralDataTable(Table t)
	{
		super();
		m_dataListeners = new HashSet<DataListener>();
		m_table = t.getDataTable();
	}
	
	public static GralDataTable toGral(Table t)
	{
		if (t instanceof GralDataTable)
			return (GralDataTable) t;
		return new GralDataTable(t);
	}
	
	@Override
	public void removeDataListener(DataListener dataListener)
	{
		m_dataListeners.remove(dataListener);
	}
	
	@Override
	public Row getRow(int row)
	{
		return new Row(this, row);
	}
	
	@Override
	public final Iterator<Comparable<?>> iterator()
	{
		return new RowIterator(m_table);
	}

	@Override
	public final void addDataListener(DataListener dataListener)
	{
		m_dataListeners.add(dataListener);
	}

	@Override
	public Statistics getStatistics()
	{
		return new Statistics(this);
	}
	
	@Override
	public final Column getColumn(int col)
	{
		return new Column(this, col);
	}

	@Override
	public Comparable<?> get(int arg0, int arg1)
	{
		return m_table.get(arg0, arg1).value();
	}

	@Override
	public int getColumnCount() 
	{
		return m_table.getColumnCount();
	}

	@Override
	public Class<? extends Comparable<?>>[] getColumnTypes() 
	{
		return m_table.getColumnTypes();
	}

	@Override
	public int getRowCount() 
	{
		return m_table.getRowCount();
	}

	@Override
	public boolean isColumnNumeric(int arg0)
	{
		return m_table.isColumnNumeric(arg0);
	}

	@Override
	protected TempTable getDataTable(boolean link_to_experiments, String... ordering)
	{
		return m_table.getDataTable(link_to_experiments, ordering);
	}

	@Override
	public TempTable getDataTable(boolean temporary)
	{
		return m_table.getDataTable(temporary);
	}

	@Override
	public NodeFunction getDependency(int row, int col)
	{
		return m_table.getDependency(row, col);
	}
	
	public String getColumnName(int col)
	{
		return m_table.getColumnName(col);
	}
}
