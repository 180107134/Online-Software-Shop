package SoftSale;


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ClientTableModel extends AbstractTableModel
{		
	private static final long serialVersionUID = 1;
	public static final int EDIT_COLUMN = 0;
	public static final int TRANSACTION_ID = 1;
	public static final int PRODUCT_NAME = 2;	
	public static final int QUANTITY = 3;
	public static final int RATING = 4;
	public static final int TRANSACTION_DATE = 5;
	public static final int APPROVALSTATUS = 6;
	
	private database _database = database.getInstance();
	ArrayList<database.StudentInfo_t> _data = new ArrayList<database.StudentInfo_t>();

	public int loadData(final database.StudentSearchCriteria_t criteria)
	{
		if (!criteria.isValid())
		{
			return 0;
		}
		if (!_database.findStudentInfo(criteria, _data))
		{
			return 0;
		}
				
		this.fireTableDataChanged();
		return _data.size();
	}
	
	@Override
	public int getColumnCount()
	{
		return 7;
	}
	
	@Override
	public int getRowCount()
	{
		return _data.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		
		if (rowIndex > _data.size() - 1)
		{
			return null;
		}
		
		if (columnIndex == EDIT_COLUMN)
		{
			return "Edit";		
		}
		if (columnIndex == TRANSACTION_ID)
		{
			return _data.get(rowIndex).TRANSACTION_ID;		
		}		

		if (columnIndex == PRODUCT_NAME)
		{
			return _data.get(rowIndex).PRODUCT_NAME;		
		}		
		if (columnIndex == QUANTITY)
		{
			return _data.get(rowIndex).QUANTITY;		
		}
		
		if (columnIndex == RATING)
		{
			return _data.get(rowIndex).RATING;
		}
		if (columnIndex == TRANSACTION_DATE)
		{
			return _data.get(rowIndex).TRANSACTION_DATE;
		}
		
		if (columnIndex == APPROVALSTATUS)
		{
			return _data.get(rowIndex).APPROVALSTATUS;
		}
		
		return null;
	}
	
	@Override
	public String getColumnName(int columnIndex)
	{
		if (columnIndex < 0)
		{
			return null;
		}
		if (columnIndex == EDIT_COLUMN)
		{
			return "EDIT";
		}
		if (columnIndex == TRANSACTION_ID)
		{
			return "TR ID";
		}			
		if (columnIndex == PRODUCT_NAME)
		{
			return "PRODUCT NAME";
		}			

		if (columnIndex == QUANTITY)
		{
			return "QUANTITY";
		}
		if (columnIndex == RATING)
		{
			return "RATING";
		}
		if (columnIndex == TRANSACTION_DATE)
		{
			return "TRANSACTION DATE";
		}
		
		if (columnIndex == APPROVALSTATUS)
		{
			return "APPROVAL STATUS";
		}
		
		return null;
	}

	public final ArrayList<database.StudentInfo_t> getData()
	{
		return _data;
	}
	
}


