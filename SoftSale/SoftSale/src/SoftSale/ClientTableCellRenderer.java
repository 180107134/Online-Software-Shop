package SoftSale;


import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class ClientTableCellRenderer extends JLabel implements TableCellRenderer
{

	private static final long serialVersionUID = 1;
	
	ClientTableCellRenderer()
	{
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
			switch(column)
			{	
				case ClientTableModel.EDIT_COLUMN:
				{
					this.setText("<html><u color=\"blue\">" + value.toString() + "</u></html>");
			
					return this;
				}
			}
		 this.setText(value.toString());

		return this;
	}	

}



