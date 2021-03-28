package SoftSale;

import java.util.ArrayList;

public class ExpressionHelper
{
	/** \brief ��� ��������� ��������� */
	public enum ExpConditionOpr_t
	{
		/** \brief ������ */
		LESS,
		/** \brief ����� */
		EQUAL,
		/** \brief ������ */
		GREATER,
		/** \brief ������ ��� ����� */
		LESS_OR_EQUAL,
		/** \brief ������ ��� ����� */
		GREATER_OR_EQUAL,
	
		LIKE
	}
	
	/** \brief ��� ���������� �������� */
	public enum ExpLogicOpr_t
	{
		/** \brief ���������� �� */
		NOT,
		/** \brief ���������� � */
		AND,
		/** \brief ���������� ��� */
		OR
	}
	
	/** \brief �������� ������� (������� WHERE) */
	final class ExpCondition
	{
		/** \brief ������������ ����*/
		String field;
		/** \brief ��� ���������� ��������*/
		ExpConditionOpr_t operator;
		/** \brief �������� ������� */
		String value;
		/** \brief �������� ������� */
		ExpLogicOpr_t logicOpr;
	}
	
	/** \brief �������� ������� SELECT*/
	final class ExpDescription_t
	{
		/** \brief ������� */
		public String table;
		/** \brief ������ ����� */
		public ArrayList<String> fields;
		/** \brief ������ ������� */
		public ArrayList<ExpCondition> conditions;
		/** \brief ����� ������������ ������� */
		public int limit = 0;
		/** \brief ��������� ������� ����������� ������ */
		public int offset = 0;
	}
	
	private ExpDescription_t _expDescription;
	
	public ExpressionHelper()
	{
		_expDescription = new ExpDescription_t();
		_expDescription.fields = new ArrayList<String>();
		_expDescription.conditions = new ArrayList<ExpCondition>();
	}
	
	/** \brief ����� ���������� ������� */
	public void reset()
	{
		_expDescription.table = "";
		_expDescription.fields.clear();
		_expDescription.conditions.clear();
	}
	
	/**
	 * \brief ������� ����� �������
	 * \param table ��� �������
	 * \note ��� ������� ������� ����� ��������� ����� FROM � �������
	 */
	public void setTable(String table)
	{
		_expDescription.table = table;
	}
	
	/**
	 * \brief ���������� ������������ ���� �������
	 * \param field ������������ ���� �������
	 * \note ����� �������� ����, �������� ������� ����� ��������� �� ��
	 * \n ������ ����� ������� ����� ��������� ����� SELECT � ������� � ��
	 */
	public void addField(String field)
	{
		for (String f:_expDescription.fields)
		{
			if (f == field)
			{
				return;
			}
		}
		
		_expDescription.fields.add(field);
	}
	
	/**
	 * \brief ���������� ������� ������
	 * \param field ���� �������, � �������� ����������� �������
	 * \param operator ��������, ����������� � ���� � �������
	 * \param value �������� ������
	 * \param logi�Opr �������� ����������� ����������� � ������� ��������� � �������
	 * \note � ������� ������� � ������� logicOpr �� �����������
	 * \n ������ ������� ������� ����� ��������� ����� WHERE � �������
	 */
	public void addCondition(String field, ExpConditionOpr_t operator, String value, ExpLogicOpr_t logicOpr)
	{
		ExpCondition condition = new ExpCondition();
		condition.field = field;
		condition.operator = operator;
		condition.value = value;
		condition.logicOpr = logicOpr;
		_expDescription.conditions.add(condition);
	}
	
	/**
	 * \brief ������� ������ ������������ �� �� �������
	 * \param limit ����������� ��������� ���������� ������� ������������ � ������� � ��	
	 * \note ����������� �� ���������� ������� ������� �� �������� ������ LIMIT � ������� � ��
	 */
	public void setLimit(int limit)
	{
		if (limit < 0)
		{
			return;
		}
		
		_expDescription.limit = limit;
	}
	
	/**
	 * 
	 */
	public void setOffset(int offset)
	{
		if (offset < 0)
		{
			return;
		}
		
		_expDescription.offset = offset;
	}
	
	/**
	 * \brief ������ ������� � ���� ������ � ���� ������
	 * \return ������ � ���� SQL-������� � �� SQLite
	 */
	public String compile()
	{
		String result = new String("SELECT ");
		
		if (_expDescription.table == "")
		{
			return "";
		}
		
		if (_expDescription.fields.size() == 0)
		{
			result += "*";
		}
		else
		{
			Boolean firstTime = true;
			
			for (String field:_expDescription.fields)
			{
				if (!firstTime)
				{
					result += ",";
				}
				
				result += field;
				firstTime = false;
			}
		}
		
		result += " FROM ";
		result += _expDescription.table;
		
		Boolean firstTime = true;
		
		for (ExpCondition condition:_expDescription.conditions)
		{
			
			if (firstTime)
			{
				result += " WHERE ";
			}
			else
			{
				result += " " + condition.logicOpr + " ";
			}
			
			result += condition.field;
			
			switch(condition.operator)
			{
			case LESS:
				result += "<";
				break;
				
			case EQUAL:
				result += "=";
				break;
				
			case GREATER:
				result += ">";
				break;
				
			case LESS_OR_EQUAL:
				result += "<=";
				break;
				
			case GREATER_OR_EQUAL:
				result += ">=";
				break;

			case LIKE:
				result += " LIKE ";
				break;
				
			}
			
			result += condition.value;
			
			firstTime = false;
		}
		
		if (_expDescription.limit > 0)
		{
			result += " LIMIT " + String.valueOf(_expDescription.limit);
		}
		
		if (_expDescription.offset > 0)
		{
			result += " OFFSET " + String.valueOf(_expDescription.offset);
		}
		
		return result;
	}
}



