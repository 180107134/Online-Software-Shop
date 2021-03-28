package SoftSale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.util.Locale;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.net.URL;
import java.io.IOException;
import java.io.File;

public class MainWindow extends JFrame
{	
	
	public static int GLOBAL_MEMBER_ID = 0;
	public static int EDIT_MEMBER_ID = 0;
	public static String GLOBAL_MEMBER_GROUP = "";
	
	public static String GLOBAL_PRODUCT_NAME = "";
	public static String GLOBAL_START_DATE = "";
	public static String GLOBAL_END_DATE = "";
	public static String GLOBAL_APPROVALSTATUS = "";	
	
	public static int TRANSACTION_ID = 0;
	public static String PRODUCT_NAME = "";
	public static int QUANTITY = 0;
	public static String RATING = "";
	public static String TRANSACTION_DATE = "";
	public static String APPROVALSTATUS = "";
	
	Settings _settings = Settings.getInstance();
	private static final long serialVersionUID = 1L;

	private final String _studentTablePaneTitle = "Result";
		
	JScrollPane _studentTablePane;
	
	static JTabbedPane _mainPane = new JTabbedPane();
	BorderLayout _mainLayout = new BorderLayout();
	JToolBar _mainToolBar = new JToolBar();
	JButton _searchButton = new JButton();
	JButton _addButton = new JButton();
	JButton _editButton = new JButton();
	JButton _exitButton = new JButton();
	JButton _exportButton = new JButton();
	
	JTable _studentTable = new JTable();
	
	ClientTableModel _studentModel = new ClientTableModel();
	
	MainWindow()
	{
		
		try {
		
		
		this.setTitle("Online Store Client");
		setResizable(false);		
		this.getContentPane().setLayout(_mainLayout);
		this.setMinimumSize(new Dimension(850, 600));
		this.setLocationRelativeTo(null);
		URL searchIconURL = getClass().getResource("icons/search.png");
		ImageIcon searchIcon = new ImageIcon(searchIconURL);
		
			
		_searchButton.setIcon(searchIcon);
		_searchButton.setFocusable(false);
		_searchButton.setToolTipText("Search");
		_searchButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SearchClientDialog _searchDialog = new SearchClientDialog();
				_searchDialog.setLocationRelativeTo(null);
				_searchDialog.setVisible(true);
				
				if (_searchDialog.isExisted())
				{
					loadStudentData(_searchDialog.criteria());
				}
				
			}
		});
		
		URL addIconURL = getClass().getResource("icons/new.png");
		ImageIcon addIcon = new ImageIcon(addIconURL);
				
		_addButton.setIcon(addIcon);
		_addButton.setFocusable(false);
		_addButton.setToolTipText("Add new order");
		_addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				AddClientDialog _addDialog = new AddClientDialog();
				_addDialog.setLocationRelativeTo(null);
				_addDialog.setVisible(true);
			}
		});	
		
		URL editIconURL = getClass().getResource("icons/user.png");
		ImageIcon editIcon = new ImageIcon(editIconURL);
				
		_editButton.setIcon(editIcon);
		_editButton.setFocusable(false);
		_editButton.setToolTipText("Edit user");
		_editButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EditMemberDialog _editDialog = new EditMemberDialog();
				_editDialog.setLocationRelativeTo(null);
				_editDialog.setVisible(true);
			}
		});	

		URL exportIconURL = getClass().getResource("icons/export.png");
		ImageIcon exportIcon = new ImageIcon(exportIconURL);
		
		_exportButton.setIcon(exportIcon);
		_exportButton.setFocusable(false);
		_exportButton.setToolTipText("Export");
		
		final JFrame thisInstance = this;
		
		_exportButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// JFilechooser with overwrite existed file dialog -->
				JFileChooser chooser = new JFileChooser()
				{
					
					private static final long serialVersionUID = 1;
					
					@Override
					public void approveSelection()
					{
						File f = getSelectedFile();
						String path = f.getAbsolutePath();
						
						if (!path.toUpperCase().endsWith(".XLS"))
						{
							path += ".xls";
						}
						
						f = new File(path);
						
						if (f.exists() && getDialogType() == SAVE_DIALOG)
						{
							int result = JOptionPane.showConfirmDialog(thisInstance,
									"The file exists, overwrite?", "Existing file",
									JOptionPane.YES_NO_CANCEL_OPTION);
							
							switch(result)
							{
							case JOptionPane.YES_OPTION:
								super.approveSelection();
								break;
								
							case JOptionPane.CANCEL_OPTION:
								super.cancelSelection();
								break;
								
							default:
								return;	
							}
						}
						super.approveSelection();
					}
				};
				// <-- JFilechooser with overwrite existed file dialog
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls");
				chooser.setFileFilter(filter);
				
				int retValue = chooser.showSaveDialog(thisInstance);
				
				if (retValue == JFileChooser.APPROVE_OPTION)
				{
					String path = chooser.getSelectedFile().getAbsolutePath();
					
					if (!path.toUpperCase().endsWith(".XLS"))
					{
						path += ".xls";
					}
					
					exportToXLS(path);
				}
			}
		});
		
		URL exitURL = getClass().getResource("icons/exit.png");
		ImageIcon exitIcon = new ImageIcon(exitURL);
		
		_exitButton.setIcon(exitIcon);
		_exitButton.setFocusable(false);
		_exitButton.setToolTipText("Exit");	

		_exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
				
			}
		});

		_mainToolBar.add(_searchButton);
		_mainToolBar.add(_addButton);
		_mainToolBar.add(_editButton);
		_mainToolBar.add(_exportButton);
		_mainToolBar.add(_exitButton);

        _studentTablePane = new JScrollPane(_studentTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    _studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	      
	    _studentTable.setModel(_studentModel);
	    _studentTable.setRowSelectionAllowed(true);
	    _studentTable.setDefaultRenderer(Object.class, new ClientTableCellRenderer());
		  
	      _studentTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
				if (e.getClickCount() < 1)
				{
					return;
				}
								
				switch(_studentTable.getSelectedColumn())
				{
				
				case ClientTableModel.EDIT_COLUMN:
				{
						
						TRANSACTION_ID = Integer.parseInt(getTableCell(_studentTable, "TR ID"));
						PRODUCT_NAME = getTableCell(_studentTable, "PRODUCT NAME");
						QUANTITY = Integer.parseInt(getTableCell(_studentTable, "QUANTITY"));
						RATING = getTableCell(_studentTable, "RATING");
						TRANSACTION_DATE = getTableCell(_studentTable, "TRANSACTION DATE");
						APPROVALSTATUS = getTableCell(_studentTable, "APPROVAL STATUS");
						EditClientDialog _editClientDialog = new EditClientDialog();
						_editClientDialog.setLocationRelativeTo(null);
						_editClientDialog.setVisible(true);
						if (_editClientDialog.isExisted())
						{
							loadStudentData(_editClientDialog.criteria());
						}
						
						break;						
					}	

				}	
				

			}
		});
		
	    resizeColumnWidthWideID(_studentTable, 0);
	    resizeColumnWidthWideID(_studentTable, 1);
	    resizeColumnWidthWideID3(_studentTable, 2);
	    resizeColumnWidthWideID2(_studentTable, 3);
	    resizeColumnWidthWideID3(_studentTable, 4);
	    resizeColumnWidthWideID3(_studentTable, 5);
	    resizeColumnWidthWideID3(_studentTable, 6);
		_mainPane.addTab(_studentTablePaneTitle, _studentTablePane);	    	
		_mainPane.setToolTipTextAt(0, "Quick search");	
		
		getContentPane().add(_mainPane, BorderLayout.CENTER);
		getContentPane().add(_mainToolBar, BorderLayout.NORTH);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	} 

	public void resizeColumnWidthWideID(JTable table, int column) {
		 DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		 colModel.getColumn(column).setMinWidth(30);
	}
	public void resizeColumnWidthWideID2(JTable table, int column) {
		 DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		 colModel.getColumn(column).setMinWidth(50);
	}
	public void resizeColumnWidthWideID3(JTable table, int column) {
		 DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		 colModel.getColumn(column).setMinWidth(150);
	}
	public void resizeColumnWidthWideID4(JTable table, int column) {
		 DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		 colModel.getColumn(column).setMinWidth(100);
	}

	private void loadStudentData(database.StudentSearchCriteria_t criteria)
	{
		if (!criteria.isValid())
		{
			return;
		}

		int size = _studentModel.loadData(criteria);

		String title = _studentTablePaneTitle + " (" + String.valueOf(size) + ")";
		
		_mainPane.setTitleAt(_mainPane.indexOfComponent(_studentTablePane), title);
		
		_mainPane.setSelectedComponent(_studentTablePane);
	}	

	
	public String getTableCell(JTable _safetyTable, String columnName) {
		String cell = "";
		try {
			cell = _safetyTable.getModel().getValueAt(_safetyTable.getSelectedRow(), 
					_safetyTable.getColumn(columnName).getModelIndex()).toString();		
			
		} catch(Exception e) {
			System.out.println(e);
			cell = "Empty";
			return cell;
		}
		return cell;
	}

	private void exportToXLS(String filename)
	{
		switch(_mainPane.getSelectedIndex())
		{
		case 0:
		{

				try {
						safetyTableModelToXLS(_studentModel, filename);
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			break;
		}

	  }

  }	

	@SuppressWarnings("unused")
	private boolean safetyTableModelToXLS(AbstractTableModel model, String inputFile) throws RowsExceededException, WriteException
	{
		
		File file = new File(inputFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		    workbook.createSheet("Report", 0);
		    WritableSheet excelSheet = workbook.getSheet(0);
		    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);

		    int col = model.getColumnCount();
		    int row = model.getRowCount();

		    for (int i = 1; i < col; i++) {
			    addCaption(excelSheet, 0, i - 1, model.getColumnName(i));		    	
		    }
		    for (int i = 1; i <= row; i++) {
		    	for (int j = 1; j < col; j++) {
		    		if (j == 1 || j == 3) {
		    			addNumberData(excelSheet, i, j - 1, Integer.parseInt(model.getValueAt(i - 1, j).toString()));
		    		}
		    		else {
			    		addStringData(excelSheet, i, j - 1, model.getValueAt(i - 1, j).toString());
		    			
		    		}
		    	}
		    }
		    
		    workbook.write();
		    workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
		      throws RowsExceededException, WriteException {
//			Label label = new Label(row, column, s, getCellFormat(Colour.PALE_BLUE));
			Label label = new Label(row, column, s);
		    sheet.addCell(label);
	}
	private void addNumberData(WritableSheet sheet, int column, int row, int i)
		      throws RowsExceededException, WriteException {
		    //Number num = new Number(row, column, i, getCellFormat(Colour.RED));
		    Number num = new Number(row, column, i);
		    sheet.addCell(num);
	}
	private void addStringData(WritableSheet sheet, int column, int row, String s)
		      throws RowsExceededException, WriteException {
		    Label label = new Label(row, column, s);
//		    Label label = new Label(row, column, s, getCellFormat(Colour.PALE_BLUE));
		    sheet.addCell(label);
	}
		
}
